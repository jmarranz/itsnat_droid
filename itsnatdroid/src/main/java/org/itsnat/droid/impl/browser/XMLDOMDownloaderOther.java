package org.itsnat.droid.impl.browser;

import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

import java.util.Map;

/**
 * Drawables, Values etc
 *
 * Created by jmarranz on 25/01/2016.
 */
public class XMLDOMDownloaderOther extends XMLDOMDownloader
{
    public XMLDOMDownloaderOther(XMLDOM xmlDOM,String pageURLBase, HttpRequestData httpRequestData, String itsNatServerVersion,Map<String,ParsedResource> urlResDownloadedMap,XMLDOMParserContext xmlDOMParserContext)
    {
        super(xmlDOM,pageURLBase,httpRequestData,itsNatServerVersion,urlResDownloadedMap,xmlDOMParserContext);
    }
}
