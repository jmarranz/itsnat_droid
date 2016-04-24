package org.itsnat.droid.impl.domparser.values;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.dom.values.DOMElemValuesArrayBase;
import org.itsnat.droid.impl.dom.values.DOMElemValuesItemArrayBase;
import org.itsnat.droid.impl.dom.values.DOMElemValuesItemNormal;
import org.itsnat.droid.impl.dom.values.DOMElemValuesItemStyle;
import org.itsnat.droid.impl.dom.values.DOMElemValuesNoChildElem;
import org.itsnat.droid.impl.dom.values.DOMElemValuesResources;
import org.itsnat.droid.impl.dom.values.DOMElemValuesStyle;
import org.itsnat.droid.impl.dom.values.XMLDOMValues;
import org.itsnat.droid.impl.dommini.DMNode;
import org.itsnat.droid.impl.dommini.DOMMiniParser;
import org.itsnat.droid.impl.dommini.DOMMiniRender;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_ARRAY;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_INTEGER_ARRAY;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_STRING_ARRAY;

/**
 * Created by jmarranz on 31/10/14.
 */
public class XMLDOMValuesParser extends XMLDOMParser<XMLDOMValues>
{
    protected XMLDOMValuesParser(XMLDOMParserContext xmlDOMParserContext)
    {
        super(xmlDOMParserContext);
    }

    public static XMLDOMValuesParser createXMLDOMValuesParser(XMLDOMParserContext xmlDOMParserContext)
    {
        return new XMLDOMValuesParser(xmlDOMParserContext);
    }

    @Override
    protected boolean isAndroidNSPrefixNeeded()
    {
        return false;
    }

    @Override
    public void parse(String markup,XMLDOMValues xmlDOMValues)
    {
        StringReader input = new StringReader(markup);
        parse(input,xmlDOMValues);
    }

    private void parse(Reader input,XMLDOMValues xmlDOMValues)
    {
        try
        {
            XmlPullParser parser = newPullParser(input);
            parse(parser,xmlDOMValues);
        }
        catch (IOException ex) { throw new ItsNatDroidException(ex); }
        catch (XmlPullParserException ex) { throw new ItsNatDroidException(ex); }
        finally
        {
            try { input.close(); }
            catch (IOException ex) { throw new ItsNatDroidException(ex); }
        }
    }

    private void parse(XmlPullParser parser,XMLDOMValues xmlDOMValues) throws IOException, XmlPullParserException
    {
        String rootElemName = getRootElementName(parser);
        parseRootElement(rootElemName,parser, xmlDOMValues);
    }

    @Override
    protected DOMElement createElement(String tagName,DOMElement parent)
    {
        if (hasChildElements(tagName))
        {
            if ("resources".equals(tagName))
            {
                if (parent != null) throw new ItsNatDroidException("<resources> element must be root");
                return new DOMElemValuesResources();
            }
            else
            {
                if ("style".equals(tagName)) return new DOMElemValuesStyle((DOMElemValuesResources) parent);
                else if (TYPE_STRING_ARRAY.equals(tagName) || TYPE_INTEGER_ARRAY.equals(tagName) || TYPE_ARRAY.equals(tagName))
                    return new DOMElemValuesArrayBase(tagName, (DOMElemValuesResources) parent);
                else if ("declare-styleable".equals(tagName))
                    throw new ItsNatDroidException("Not supported yet:" + tagName);
            }

            throw new ItsNatDroidException("Unrecognized element name:" + tagName);
        }
        else
        {
            if (parent instanceof DOMElemValuesStyle)
                return new DOMElemValuesItemStyle((DOMElemValuesStyle)parent);
            else if (parent instanceof DOMElemValuesArrayBase)
                return new DOMElemValuesItemArrayBase((DOMElemValuesArrayBase)parent);
            else
                return new DOMElemValuesItemNormal(tagName, (DOMElemValuesResources) parent);
        }
    }

    @Override
    protected void processChildElements(DOMElement parentElement,XmlPullParser parser,XMLDOM xmlDOM) throws IOException, XmlPullParserException
    {
        if (parentElement instanceof DOMElemValuesNoChildElem)
        {
            DOMElemValuesNoChildElem parentElementNoChildElem = (DOMElemValuesNoChildElem)parentElement;

            // Lo normal es que esperemos un único nodo de texto hijo, pero también podemos esperar un texto con HTML, que será tolerado y procesado por Resources.getText()

            DMNode[] nodeList = DOMMiniParser.parse(parser);
            String text = DOMMiniRender.toString(nodeList);

            /*
            while (parser.next() != XmlPullParser.TEXT) ;

            String text = parser.getText();

            while (parser.next() != XmlPullParser.END_TAG) ;
            */

            DOMAttr valueAsDOMAttr = parentElementNoChildElem.setTextNode(text); // El nodo de texto lo tratamos de forma especial como un atributo para resolver si es asset o remote y así cargarlo
            addDOMAttr(parentElementNoChildElem,valueAsDOMAttr, xmlDOM);
        }
        else
        {
            super.processChildElements(parentElement, parser, xmlDOM);
        }
    }


    public static boolean hasChildElements(String elemName)
    {
        // http://developer.android.com/guide/topics/resources/available-resources.html
        return "resources".equals(elemName) || "style".equals(elemName) || "declare-styleable".equals(elemName) ||
                TYPE_STRING_ARRAY.equals(elemName) || TYPE_INTEGER_ARRAY.equals(elemName) || TYPE_ARRAY.equals(elemName);
    }
}
