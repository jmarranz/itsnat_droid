package org.itsnat.droid.impl.domparser.layout;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.layout.DOMElemLayout;
import org.itsnat.droid.impl.dom.layout.DOMElemMerge;
import org.itsnat.droid.impl.dom.layout.DOMElemView;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;


/**
 * Created by jmarranz on 27/10/14.
 */
public abstract class XMLDOMLayoutParser extends XMLDOMParser
{
    public enum LayoutType { PAGE, PAGE_FRAGMENT, STANDALONE };

    protected XMLDOMLayoutParser(XMLDOMParserContext xmlDOMParserContext)
    {
        super(xmlDOMParserContext);
    }

    public static XMLDOMLayoutParser createXMLDOMLayoutParser(String itsNatServerVersion,LayoutType layoutType,XMLDOMParserContext xmlDOMParserContext)
    {
        XMLDOMLayoutParser layoutParser;
        if (layoutType == LayoutType.PAGE)
        {
            if (itsNatServerVersion != null)
                layoutParser = new XMLDOMLayoutParserPageItsNat(xmlDOMParserContext,itsNatServerVersion);
            else
                layoutParser = new XMLDOMLayoutParserPageNotItsNat(xmlDOMParserContext);
        }
        else if (layoutType == LayoutType.PAGE_FRAGMENT) // Los fragmentos pueden usarse en páginas generadas por ItsNat y no generadas por ItsNat (itsNatServerVersion es indiferente)
            layoutParser = new XMLDOMLayoutParserPageFragment(xmlDOMParserContext);
        else if (layoutType == LayoutType.STANDALONE)
            layoutParser = new XMLDOMLayoutParserStandalone(xmlDOMParserContext);
        else
            layoutParser = null; // Internal Error

        return layoutParser;
    }

    public void parse(String markup,XMLDOMLayout domLayout)
    {
        StringReader input = new StringReader(markup);
        parse(input,domLayout);
    }

    private void parse(Reader input,XMLDOMLayout domLayout)
    {
        try
        {
            XmlPullParser parser = newPullParser(input);
            parse(parser,domLayout);
        }
        catch (IOException ex) { throw new ItsNatDroidException(ex); }
        catch (XmlPullParserException ex) { throw new ItsNatDroidException(ex); }
        finally
        {
            try { input.close(); }
            catch (IOException ex) { throw new ItsNatDroidException(ex); }
        }
    }

    private void parse(XmlPullParser parser,XMLDOMLayout domLayout) throws IOException, XmlPullParserException
    {
        String rootElemName = getRootElementName(parser);
        parseRootElement(rootElemName,parser,domLayout);
    }

    public abstract XMLDOMLayout createXMLDOMLayout();


    @Override
    protected DOMElement createElement(String name,DOMElement parent)
    {
        /*if ("include".equals(name))
            return new DOMInclude(name,(DOMView)parent);
        else */
        if ("merge".equals(name))
        {
            if (parent != null) throw new ItsNatDroidException("<merge> only can be used as a root element of the XML layout");
            return new DOMElemMerge(name, null);
        }
        else
            return new DOMElemView(name,(DOMElemLayout)parent);
    }
}
