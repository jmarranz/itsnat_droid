package org.itsnat.droid.impl.domparser.layout;

import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageItsNat;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by jmarranz on 14/11/14.
 */
public class XMLDOMLayoutParserPageItsNat extends XMLDOMLayoutParserPage
{
    protected final String itsNatServerVersion; // NO null

    public XMLDOMLayoutParserPageItsNat(XMLDOMParserContext xmlDOMParserContext, String itsNatServerVersion)
    {
        super(xmlDOMParserContext);
        this.itsNatServerVersion = itsNatServerVersion;
    }

    @Override
    protected void addDOMScriptInline(XmlPullParser parser, XMLDOMLayoutPage domLayout) throws IOException, XmlPullParserException
    {
        // Aunque sea una página generada por ItsNat puede tener el scripting desactivado y no generar load script
        boolean isLoadScript = parser.getAttributeCount() == 1 &&
                                "id".equals(parser.getAttributeName(0)) &&
                                "itsnat_load_script".equals(parser.getAttributeValue(0));

        if (isLoadScript)
        {
            // Tratamiento especial
            while (parser.next() != XmlPullParser.TEXT) /*nop*/ ;
            String code = parser.getText();

            XMLDOMLayoutPageItsNat xmlDOMLayoutPageItsNat = (XMLDOMLayoutPageItsNat)domLayout;
            xmlDOMLayoutPageItsNat.setLoadInitScript(code);
        }
        else
            super.addDOMScriptInline(parser, domLayout);
    }

    @Override
    public XMLDOMLayout createXMLDOMLayout()
    {
        return new XMLDOMLayoutPageItsNat();
    }
}
