package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

import java.util.LinkedList;

/**
 * Created by jmarranz on 24/01/2016.
 */
public class XMLDOMDownloader
{
    protected XMLDOM xmlDOM;
    protected String pageURLBase;
    protected HttpRequestData httpRequestData;
    protected XMLDOMRegistry xmlDOMRegistry;
    protected AssetManager assetManager;

    public XMLDOMDownloader(XMLDOM xmlDOM,String pageURLBase, HttpRequestData httpRequestData, XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager)
    {
        this.xmlDOM = xmlDOM;
        this.pageURLBase = pageURLBase;
        this.httpRequestData = httpRequestData;
        this.xmlDOMRegistry = xmlDOMRegistry;
        this.assetManager = assetManager;
    }

    public static XMLDOMDownloader createXMLDOMDownloader(XMLDOM xmlDOM,String pageURLBase, HttpRequestData httpRequestData, XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager)
    {
        if (xmlDOM instanceof XMLDOMLayoutPage)
            return XMLDOMLayoutPageDownloader.createXMLDOMLayoutPageDownloader((XMLDOMLayoutPage)xmlDOM,pageURLBase,httpRequestData,xmlDOMRegistry,assetManager);
        else
            return new XMLDOMDownloaderOther(xmlDOM,pageURLBase,httpRequestData,xmlDOMRegistry,assetManager);
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

        HttpResourceDownloader resDownloader = new HttpResourceDownloader(pageURLBase, httpRequestData, xmlDOMRegistry, assetManager);
        resDownloader.downloadResources(attrRemoteList);
    }

}
