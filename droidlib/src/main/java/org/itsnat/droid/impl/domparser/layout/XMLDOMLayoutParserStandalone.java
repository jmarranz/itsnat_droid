package org.itsnat.droid.impl.domparser.layout;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.dom.layout.DOMElemView;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by jmarranz on 27/10/14.
 */
public class XMLDOMLayoutParserStandalone extends XMLDOMLayoutParser
{
    public XMLDOMLayoutParserStandalone(XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager)
    {
        super(xmlDOMRegistry,assetManager);
    }

    @Override
    protected void parseScriptElement(XmlPullParser parser, DOMElemView viewParent,XMLDOM xmlDOM) throws IOException, XmlPullParserException
    {
        android.util.Log.v("XMLDOMLayoutParserStand","<script> elements are ignored in standalone layouts");

        while (parser.next() != XmlPullParser.END_TAG) /*nop*/ ;
    }
}
