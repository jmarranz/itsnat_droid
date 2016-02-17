package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

/**
 * Created by jmarranz on 24/01/2016.
 */
public class XMLDOMDownloader
{
    protected final XMLDOM xmlDOM;
    protected final String pageURLBase;
    protected final HttpRequestData httpRequestData;
    protected final String itsNatServerVersion;
    protected final Map<String,ParsedResource> urlResDownloadedMap;
    protected final XMLDOMRegistry xmlDOMRegistry;
    protected final AssetManager assetManager;
    protected final Locale locale;

    public XMLDOMDownloader(XMLDOM xmlDOM,String pageURLBase, HttpRequestData httpRequestData, String itsNatServerVersion,Map<String,ParsedResource> urlResDownloadedMap,
                            XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager,Locale locale)
    {
        this.xmlDOM = xmlDOM;
        this.pageURLBase = pageURLBase;
        this.httpRequestData = httpRequestData;
        this.itsNatServerVersion = itsNatServerVersion;
        this.urlResDownloadedMap = urlResDownloadedMap;
        this.xmlDOMRegistry = xmlDOMRegistry;
        this.assetManager = assetManager;
        this.locale = locale;
    }

    public static XMLDOMDownloader createXMLDOMDownloader(XMLDOM xmlDOM,String pageURLBase, HttpRequestData httpRequestData, String itsNatServerVersion,
                                Map<String,ParsedResource> urlResDownloadedMap,XMLDOMRegistry xmlDOMRegistry,
                                AssetManager assetManager,Locale locale)
    {
        if (xmlDOM instanceof XMLDOMLayoutPage)
            return XMLDOMLayoutPageDownloader.createXMLDOMLayoutPageDownloader((XMLDOMLayoutPage)xmlDOM,pageURLBase,httpRequestData,itsNatServerVersion,urlResDownloadedMap,xmlDOMRegistry,assetManager,locale);
        else
            return new XMLDOMDownloaderOther(xmlDOM,pageURLBase,httpRequestData,itsNatServerVersion,urlResDownloadedMap,xmlDOMRegistry,assetManager,locale);
    }

    public void downloadRemoteResources() throws Exception
    {
        LinkedList<DOMAttrRemote> attrRemoteList = xmlDOM.getDOMAttrRemoteList();
        if (attrRemoteList != null)
        {
            downloadResources(attrRemoteList);
        }
    }

    protected void downloadResources(LinkedList<DOMAttrRemote> attrRemoteList) throws Exception
    {
        // llena los elementos de DOMAttrRemote attrRemoteList con el recurso descargado que le corresponde

        HttpResourceDownloader resDownloader = new HttpResourceDownloader(pageURLBase, httpRequestData,itsNatServerVersion,urlResDownloadedMap,xmlDOMRegistry, assetManager,locale);
        resDownloader.downloadResources(attrRemoteList);
    }

}
