package org.itsnat.droid.impl.domparser.layout;

import android.content.res.AssetManager;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by jmarranz on 14/11/14.
 */
public class XMLDOMLayoutParserItsNatPage extends XMLDOMLayoutParserItsNatPageOrFragment
{
    protected final String itsNatServerVersion; // NO null

    public XMLDOMLayoutParserItsNatPage(XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager, String itsNatServerVersion)
    {
        super(xmlDOMRegistry,assetManager);
        this.itsNatServerVersion = itsNatServerVersion;
    }

    @Override
    protected void addDOMScriptInline(XmlPullParser parser, XMLDOMLayout domLayout) throws IOException, XmlPullParserException
    {
        boolean isLoadScript = parser.getAttributeCount() == 1 &&
                                "id".equals(parser.getAttributeName(0)) &&
                                "itsnat_load_script".equals(parser.getAttributeValue(0));

        if (isLoadScript)
        {
            // Tratamiento especial
            while (parser.next() != XmlPullParser.TEXT) /*nop*/ ;
            String code = parser.getText();

            domLayout.setLoadScript(code);
        }
        else
            super.addDOMScriptInline(parser, domLayout);
    }

}
