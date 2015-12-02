package org.itsnat.droid.impl.domparser.layout;

import android.content.res.AssetManager;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.dom.layout.DOMInclude;
import org.itsnat.droid.impl.dom.layout.DOMMerge;
import org.itsnat.droid.impl.dom.layout.DOMView;
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

    public static XMLDOMLayoutParser createXMLDOMLayoutParser(String itsNatServerVersion,boolean remotePageOrFrag,boolean loadingRemotePage,XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager)
    {
        XMLDOMLayoutParser layoutParser;
        if (remotePageOrFrag)
            layoutParser = loadingRemotePage ? new XMLDOMLayoutParserPage(xmlDOMRegistry, assetManager, itsNatServerVersion) :
                                         new XMLDOMLayoutParserFragment(xmlDOMRegistry, assetManager);
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
            return new DOMMerge(name, null);
        }
        else
            return new DOMView(name,parent);
    }

    @Override
    protected DOMElement processElement(String name, DOMElement parentElement, XmlPullParser parser,XMLDOM xmlDOM) throws IOException, XmlPullParserException
    {
        if (name.equals("script"))
        {
            parseScriptElement(parser,(DOMView)parentElement, xmlDOM);
            return null; // Ignorar porque "desaparece"
        }
        else return super.processElement(name,parentElement,parser, xmlDOM);
    }

    @Override
    public DOMAttr createDOMAttr(DOMElement element,String namespaceURI, String name, String value, XMLDOM xmlDOMParent)
    {
        DOMAttr domAttr = super.createDOMAttr(element,namespaceURI,name,value,xmlDOMParent);

        if (element instanceof DOMView)
        {
            DOMView domView = (DOMView)element;
            if (namespaceURI == null && "style".equals(name))
                domView.setStyleAttr(value);
        }
        else if (element instanceof DOMInclude)
        {
            DOMInclude domInc = (DOMInclude)element;
            if ((namespaceURI == null) && "layout".equals(name))
                domInc.setLayout(value);
        }
        return domAttr;
    }

    protected abstract void parseScriptElement(XmlPullParser parser,DOMView viewParent,XMLDOM xmlDOM) throws IOException, XmlPullParserException;
}
