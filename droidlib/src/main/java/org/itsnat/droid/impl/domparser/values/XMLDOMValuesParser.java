package org.itsnat.droid.impl.domparser.values;

import android.content.res.AssetManager;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.dom.values.DOMElemValues;
import org.itsnat.droid.impl.dom.values.XMLDOMValues;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by jmarranz on 31/10/14.
 */
public class XMLDOMValuesParser extends XMLDOMParser
{
    public XMLDOMValuesParser(XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager)
    {
        super(xmlDOMRegistry,assetManager);
    }

    public static XMLDOMValuesParser createXMLDOMValuesParser(XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager)
    {
        return new XMLDOMValuesParser(xmlDOMRegistry,assetManager);
    }

    public static boolean isResourceTypeValues(String resourceType)
    {
        return "style".equals(resourceType) || "color".equals(resourceType) || "dimen".equals(resourceType);
    }

    @Override
    protected boolean isAndroidNSPrefixNeeded()
    {
        return false;
    }

    public XMLDOMValues parse(String markup)
    {
        StringReader input = new StringReader(markup);
        return parse(input);
    }

    private XMLDOMValues parse(Reader input)
    {
        try
        {
            XmlPullParser parser = newPullParser(input);
            return parse(parser);
        }
        catch (IOException ex) { throw new ItsNatDroidException(ex); }
        catch (XmlPullParserException ex) { throw new ItsNatDroidException(ex); }
        finally
        {
            try { input.close(); }
            catch (IOException ex) { throw new ItsNatDroidException(ex); }
        }
    }

    private XMLDOMValues parse(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        XMLDOMValues xmlDOMValues = new XMLDOMValues();
        String rootElemName = getRootElementName(parser);
        parseRootElement(rootElemName,parser, xmlDOMValues);
        return xmlDOMValues;
    }

    @Override
    protected DOMElement createElement(String name,DOMElement parent)
    {
        return new DOMElemValues(name,(DOMElemValues)parent);
    }

    @Override
    protected void processChildElements(DOMElement parentElement,XmlPullParser parser,XMLDOM xmlDOM) throws IOException, XmlPullParserException
    {
        if (!hasChildElements(parentElement))
        {
            // Esperamos un único nodo de texto hijo, no toleramos comentarios ni similares
            while (parser.next() != XmlPullParser.TEXT) ; // Ignoramos comentarios etc

            String text = parser.getText();
            ((DOMElemValues) parentElement).setText(text);

            while (parser.next() != XmlPullParser.END_TAG) ; // Ignoramos comentarios etc
        }
        else
        {
            super.processChildElements(parentElement, parser, xmlDOM);
        }
    }

    public static boolean hasChildElements(DOMElement parentElement)
    {
        // http://developer.android.com/guide/topics/resources/available-resources.html
        String name = parentElement.getName();
        return "resources".equals(name) || "string-array".equals(name) || "style".equals(name) || "declare-styleable".equals(name);
    }
}
