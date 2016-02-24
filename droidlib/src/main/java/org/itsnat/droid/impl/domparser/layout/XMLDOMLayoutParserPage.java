package org.itsnat.droid.impl.domparser.layout;

import android.content.res.AssetManager;
import android.content.res.Configuration;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.dom.layout.DOMScriptInline;
import org.itsnat.droid.impl.dom.layout.DOMScriptRemote;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by jmarranz on 27/10/14.
 */
public abstract class XMLDOMLayoutParserPage extends XMLDOMLayoutParser
{
    protected XMLDOMLayoutParserPage(XMLDOMParserContext xmlDOMParserContext)
    {
        super(xmlDOMParserContext);
    }

    @Override
    protected DOMElement processElement(String name, DOMElement parentElement, XmlPullParser parser, XMLDOM xmlDOM) throws IOException, XmlPullParserException
    {
        if (name.equals("script"))
        {
            parseScriptElement(parser,(XMLDOMLayoutPage)xmlDOM);
            return null; // Ignorar porque "desaparece"
        }
        else return super.processElement(name,parentElement,parser, xmlDOM);
    }

    protected void parseScriptElement(XmlPullParser parser, XMLDOMLayoutPage xmlDOM) throws IOException, XmlPullParserException
    {
        String src = findAttributeFromParser(null, "src", parser);
        if (src != null)
        {
            addDOMScriptRemote(src, xmlDOM);
        }
        else
        {
            addDOMScriptInline(parser, xmlDOM);
        }

        while (parser.next() != XmlPullParser.END_TAG) /*nop*/ ;
    }

    protected void addDOMScriptRemote(String src, XMLDOMLayoutPage xmlDOM)
    {
        // Si loadingPage es true es el caso de carga de página, pero si serverVersion es null dicha página
        // NO es servida por ItsNat, tenemos que cargar asíncronamente el archivo script pues este es el hilo UI :(
        // Si loadInitScript es null estamos en un evento (inserción de un fragment)

        DOMScriptRemote script = new DOMScriptRemote(src);
        xmlDOM.addDOMScript(script);
    }

    protected void addDOMScriptInline(XmlPullParser parser, XMLDOMLayoutPage xmlDOM) throws IOException, XmlPullParserException
    {
        while (parser.next() != XmlPullParser.TEXT) /*nop*/ ;

        String code = parser.getText();

        DOMScriptInline script = new DOMScriptInline(code);
        xmlDOM.addDOMScript(script);
    }
}
