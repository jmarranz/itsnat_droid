package org.itsnat.droid.impl.browser.servernotitsnat;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.browser.HttpRequestData;
import org.itsnat.droid.impl.browser.XMLDOMLayoutPageDownloader;
import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageNotItsNat;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

import java.util.Locale;
import java.util.Map;

/**
 * Created by jmarranz on 25/01/2016.
 */
public class XMLDOMLayoutPageNotItsNatDownloader extends XMLDOMLayoutPageDownloader
{
    public XMLDOMLayoutPageNotItsNatDownloader(XMLDOMLayoutPageNotItsNat xmlDOM,String pageURLBase, HttpRequestData httpRequestData, String itsNatServerVersion,
                                               Map<String,ParsedResource> urlResDownloadedMap,XMLDOMRegistry xmlDOMRegistry,
                                               AssetManager assetManager,Locale locale)
    {
        super(xmlDOM,pageURLBase,httpRequestData,itsNatServerVersion,urlResDownloadedMap,xmlDOMRegistry,assetManager,locale);
    }
}
