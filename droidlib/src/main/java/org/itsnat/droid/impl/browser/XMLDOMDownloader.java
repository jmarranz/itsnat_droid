package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by jmarranz on 24/01/2016.
 */
public class XMLDOMDownloader
{
    protected XMLDOM xmlDOM;
    protected String pageURLBase;
    protected HttpRequestData httpRequestData;
    protected String itsNatServerVersion;
    protected XMLDOMRegistry xmlDOMRegistry;
    protected AssetManager assetManager;

    public XMLDOMDownloader(XMLDOM xmlDOM,String pageURLBase, HttpRequestData httpRequestData, String itsNatServerVersion, XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager)
    {
        this.xmlDOM = xmlDOM;
        this.pageURLBase = pageURLBase;
        this.httpRequestData = httpRequestData;
        this.itsNatServerVersion = itsNatServerVersion;
        this.xmlDOMRegistry = xmlDOMRegistry;
        this.assetManager = assetManager;
    }

    public static XMLDOMDownloader createXMLDOMDownloader(XMLDOM xmlDOM,String pageURLBase, HttpRequestData httpRequestData, String itsNatServerVersion, XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager)
    {
        if (xmlDOM instanceof XMLDOMLayoutPage)
            return XMLDOMLayoutPageDownloader.createXMLDOMLayoutPageDownloader((XMLDOMLayoutPage)xmlDOM,pageURLBase,httpRequestData,itsNatServerVersion,xmlDOMRegistry,assetManager);
        else
            return new XMLDOMDownloaderOther(xmlDOM,pageURLBase,httpRequestData,itsNatServerVersion,xmlDOMRegistry,assetManager);
    }

    public void downloadRemoteResources(Map<String,ParsedResource> urlResDownloadedMap) throws Exception
    {
        LinkedList<DOMAttrRemote> attrRemoteList = xmlDOM.getDOMAttrRemoteList();
        if (attrRemoteList != null)
        {
            downloadResources(attrRemoteList,urlResDownloadedMap);
        }
    }

    protected void downloadResources(LinkedList<DOMAttrRemote> attrRemoteList,Map<String,ParsedResource> urlResDownloadedMap) throws Exception
    {
        // llena los elementos de DOMAttrRemote attrRemoteList con el recurso descargado que le corresponde

        HttpResourceDownloader resDownloader = new HttpResourceDownloader(pageURLBase, httpRequestData,itsNatServerVersion,xmlDOMRegistry, assetManager);
        resDownloader.downloadResources(attrRemoteList,urlResDownloadedMap);
    }

}
