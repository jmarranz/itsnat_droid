package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

/**
 * Drawables, Values etc
 *
 * Created by jmarranz on 25/01/2016.
 */
public class XMLDOMDownloaderOther extends XMLDOMDownloader
{
    public XMLDOMDownloaderOther(XMLDOM xmlDOM,String pageURLBase, HttpRequestData httpRequestData, XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager)
    {
        super(xmlDOM,pageURLBase,httpRequestData,xmlDOMRegistry,assetManager);
    }
}
