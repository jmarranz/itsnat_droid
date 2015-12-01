package org.itsnat.droid.impl.domparser.layout;

import android.content.res.AssetManager;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.dom.layout.DOMInclude;
import org.itsnat.droid.impl.dom.layout.DOMMerge;
import org.itsnat.droid.impl.dom.layout.DOMView;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
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

    public static XMLDOMLayoutParser createXMLDOMLayoutParser(String itsNatServerVersion,boolean loadingPage,boolean remotePageOrFrag,XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager)
    {
        XMLDOMLayoutParser layoutParser;
        if (remotePageOrFrag)
            layoutParser = loadingPage ? new XMLDOMLayoutParserPage(xmlDOMRegistry, assetManager, itsNatServerVersion) :
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
        if ("include".equals(name))
            return new DOMInclude(name,(DOMView)parent);
        else if ("merge".equals(name))
            return new DOMMerge(name,(DOMView)parent);
        else
            return new DOMView(name,(DOMView)parent);
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
    protected void addDOMAttr(DOMElement element, String namespaceURI, String name, String value, XMLDOM xmlDOM)
    {
        if (element instanceof DOMView)
        {
            DOMView domView = (DOMView)element;
            if (namespaceURI == null && "style".equals(name))
                domView.setStyleAttr(value);
            else
                super.addDOMAttr(element, namespaceURI, name, value, xmlDOM);
        }
        else if (element instanceof DOMInclude)
        {
            DOMInclude domInc = (DOMInclude)element;
            if ((namespaceURI == null) && "layout".equals(name))
                domInc.setLayout(value);
            else
                super.addDOMAttr(element, namespaceURI, name, value, xmlDOM);
        }
        else
            super.addDOMAttr(element, namespaceURI, name, value, xmlDOM);
    }

    protected abstract void parseScriptElement(XmlPullParser parser,DOMView viewParent,XMLDOM xmlDOM) throws IOException, XmlPullParserException;
}
