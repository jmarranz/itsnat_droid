package org.itsnat.droid.impl.domparser.layout;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

/**
 * Created by jmarranz on 27/10/14.
 */
public class XMLDOMLayoutParserStandalone extends XMLDOMLayoutParser
{
    public XMLDOMLayoutParserStandalone(XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager)
    {
        super(xmlDOMRegistry,assetManager);
    }

    /*
    @Override
    protected void parseScriptElement(XmlPullParser parser, DOMElement parentElement,XMLDOM xmlDOM) throws IOException, XmlPullParserException
    {
        android.util.Log.v("XMLDOMLayoutParserStand","<script> elements are ignored in standalone layouts");

        while (parser.next() != XmlPullParser.END_TAG)  ; // nop
    }
    */
}
