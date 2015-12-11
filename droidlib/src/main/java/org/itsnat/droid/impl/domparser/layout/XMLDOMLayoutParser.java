package org.itsnat.droid.impl.domparser.layout;

import android.content.res.AssetManager;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.dom.layout.DOMElemMerge;
import org.itsnat.droid.impl.dom.layout.DOMElemView;
import org.itsnat.droid.impl.dom.layout.DOMScriptInline;
import org.itsnat.droid.impl.dom.layout.DOMScriptRemote;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
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
    public XMLDOMLayoutParser(XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager)
    {
        super(xmlDOMRegistry,assetManager);
    }

    public static XMLDOMLayoutParser createXMLDOMLayoutParser(String itsNatServerVersion,boolean loadingRemotePage,XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager)
    {
        XMLDOMLayoutParser layoutParser;
        if (itsNatServerVersion != null)
            layoutParser = loadingRemotePage ? new XMLDOMLayoutParserItsNatPage(xmlDOMRegistry, assetManager, itsNatServerVersion) :
                                         new XMLDOMLayoutParserItsNatFragment(xmlDOMRegistry, assetManager);
        else
            layoutParser = new XMLDOMLayoutParserStandalone(xmlDOMRegistry, assetManager);
        return layoutParser;
    }

    public XMLDOMLayout parse(String markup)
    {
        StringReader input = new StringReader(markup);
        return parse(input);
    }

    private XMLDOMLayout parse(Reader input)
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

    private XMLDOMLayout parse(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        XMLDOMLayout domLayout = new XMLDOMLayout();
        String rootElemName = getRootElementName(parser);
        parseRootElement(rootElemName,parser,domLayout);
        return domLayout;
    }

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
            return new DOMElemView(name,parent);
    }

    @Override
    protected DOMElement processElement(String name, DOMElement parentElement, XmlPullParser parser,XMLDOM xmlDOM) throws IOException, XmlPullParserException
    {
        if (name.equals("script"))
        {
            parseScriptElement(parser,parentElement, xmlDOM);
            return null; // Ignorar porque "desaparece"
        }
        else return super.processElement(name,parentElement,parser, xmlDOM);
    }

    @Override
    public DOMAttr createDOMAttr(DOMElement element,String namespaceURI, String name, String value, XMLDOM xmlDOMParent)
    {
        DOMAttr domAttr = super.createDOMAttr(element,namespaceURI,name,value,xmlDOMParent);

        if (element instanceof DOMElemView)
        {
            DOMElemView domElemView = (DOMElemView)element;
            if (namespaceURI == null && "style".equals(name))
                domElemView.setStyleAttr(value);
        }
        return domAttr;
    }

    protected void parseScriptElement(XmlPullParser parser, DOMElement parentElement,XMLDOM xmlDOM) throws IOException, XmlPullParserException
    {
        XMLDOMLayout domLayout = (XMLDOMLayout) xmlDOM;

        String src = findAttributeFromParser(null, "src", parser);
        if (src != null)
        {
            addDOMScriptRemote(src, domLayout);
        }
        else
        {
            addDOMScriptInline(parser, domLayout);
        }

        while (parser.next() != XmlPullParser.END_TAG) /*nop*/ ;
    }

    protected void addDOMScriptRemote(String src, XMLDOMLayout domLayout)
    {
        // Si loadingPage es true es el caso de carga de página, pero si serverVersion es null dicha página
        // NO es servida por ItsNat, tenemos que cargar asíncronamente el archivo script pues este es el hilo UI :(
        // Si loadScript es null estamos en un evento (inserción de un fragment)

        DOMScriptRemote script = new DOMScriptRemote(src);
        domLayout.addDOMScript(script);
    }

    protected void addDOMScriptInline(XmlPullParser parser, XMLDOMLayout domLayout) throws IOException, XmlPullParserException
    {
        while (parser.next() != XmlPullParser.TEXT) /*nop*/ ;

        String code = parser.getText();

        DOMScriptInline script = new DOMScriptInline(code);
        domLayout.addDOMScript(script);
    }

    // protected abstract void parseScriptElement(XmlPullParser parser,DOMElement parentElement,XMLDOM xmlDOM) throws IOException, XmlPullParserException;
}
