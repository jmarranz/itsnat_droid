package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;
import android.content.res.Configuration;

import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageFragment;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

import java.util.Map;

/**
 * Created by jmarranz on 25/01/2016.
 */
public class XMLDOMLayoutPageFragmentDownloader extends XMLDOMLayoutPageDownloader
{
    public XMLDOMLayoutPageFragmentDownloader(XMLDOMLayoutPageFragment xmlDOM,String pageURLBase, HttpRequestData httpRequestData, String itsNatServerVersion,
                                              Map<String,ParsedResource> urlResDownloadedMap,XMLDOMParserContext xmlDOMParserContext)
    {
        super(xmlDOM,pageURLBase,httpRequestData,itsNatServerVersion,urlResDownloadedMap,xmlDOMParserContext);
    }
}
