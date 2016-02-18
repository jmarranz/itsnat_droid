package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;
import android.content.res.Configuration;

import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.dom.ParsedResourceXMLDOM;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by jmarranz on 12/11/14.
 */
public class HttpResourceDownloader
{
    protected final String pageURLBase;
    protected final HttpRequestData httpRequestData;
    protected final String itsNatServerVersion;
    protected final Map<String,ParsedResource> urlResDownloadedMap;
    protected final XMLDOMRegistry xmlDOMRegistry;
    protected final AssetManager assetManager;
    protected final Configuration configuration;

    public HttpResourceDownloader(String pageURLBase,HttpRequestData httpRequestData, String itsNatServerVersion,Map<String,ParsedResource> urlResDownloadedMap,
                                  XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager,Configuration configuration)
    {
        this.pageURLBase = pageURLBase;
        this.httpRequestData = httpRequestData;
        this.itsNatServerVersion = itsNatServerVersion;
        this.urlResDownloadedMap = urlResDownloadedMap;
        this.xmlDOMRegistry = xmlDOMRegistry;
        this.assetManager = assetManager;
        this.configuration = configuration;
    }

    public List<HttpRequestResultOKImpl> downloadResources(List<DOMAttrRemote> attrRemoteList) throws Exception
    {
        List<HttpRequestResultOKImpl> resultList = Collections.synchronizedList(new ArrayList<HttpRequestResultOKImpl>()); // Necesario synchronizedList(), pues se comparte entre hilos
        downloadResources(attrRemoteList,resultList);
        return resultList;
    }

    private void downloadResources(List<DOMAttrRemote> attrRemoteList,List<HttpRequestResultOKImpl> resultList) throws Exception
    {
        int len = attrRemoteList.size();
        final Thread[] threadArray = new Thread[len];
        final Exception[] exList = new Exception[len];

        {
            int i = 0;
            final boolean[] stop = new boolean[1];
            for (DOMAttrRemote attr : attrRemoteList)
            {
                Thread thread = downloadResource(attr, stop, i,resultList, exList);
                threadArray[i] = thread;
                i++;
            }
        }

        {
            for (int i = 0; i < threadArray.length; i++)
            {
                threadArray[i].join();
            }

            for (int i = 0; i < exList.length; i++)
            {
                if (exList[i] != null)
                    throw exList[i]; // La primera que encontremos (es raro que haya más de una)
            }
        }
    }

    private Thread downloadResource(final DOMAttrRemote attr, final boolean[] stop, final int i,
                                    final List<HttpRequestResultOKImpl> resultList,final Exception[] exList) throws Exception
    {
        Thread thread = new Thread()
        {
            public void run()
            {
                if (stop[0]) return;
                try
                {
                    String resourceMime = attr.getResourceMime();
                    String absURL = HttpUtil.composeAbsoluteURL(attr.getLocation(), pageURLBase);
                    ParsedResource parsedResource;
                    synchronized(urlResDownloadedMap)
                    {
                        // El objetivo de esto es evitar cargar el mismo recurso (ej archivo XML) muchas veces durante el mismo proceso de carga en hilos no UI, más aun cuando en un archivo "values" ha referencias recursivas
                        parsedResource = urlResDownloadedMap.get(absURL);
                    }
                    if (parsedResource != null)
                    {
                        attr.setResource(parsedResource.copy());
                        return;
                    }
                    HttpRequestResultOKImpl resultResource = HttpUtil.httpGet(absURL, httpRequestData, null, resourceMime);
                    processHttpRequestResultResource(absURL,attr, resultResource, resultList);
                }
                catch (Exception ex)
                {
                    exList[i] = ex;
                    stop[0] = true;
                }
            }
        };
        thread.start();
        return thread;
    }

    private void processHttpRequestResultResource(String absURL,DOMAttrRemote attr, HttpRequestResultOKImpl resultRes, List<HttpRequestResultOKImpl> resultList) throws Exception
    {
        // Método llamado en multihilo

        resultList.add(resultRes);

        ParsedResource resource = XMLDOMParser.parseDOMAttrRemote(attr, resultRes, xmlDOMRegistry, assetManager,configuration);
        synchronized(urlResDownloadedMap)
        {
            urlResDownloadedMap.put(absURL,resource); // No pasa nada si dos hilos con el mismo absURL-resource hacen put seguidos
        }

        if (resource instanceof ParsedResourceXMLDOM)
        {
            XMLDOM xmlDOM = ((ParsedResourceXMLDOM)resource).getXMLDOM();
            String absURLContainer = HttpUtil.composeAbsoluteURL(attr.getLocation(), pageURLBase);
            String pageURLBaseContainer = HttpUtil.getBasePathOfURL(absURLContainer);
            XMLDOMDownloader downloader = XMLDOMDownloader.createXMLDOMDownloader(xmlDOM,pageURLBaseContainer, httpRequestData, itsNatServerVersion,urlResDownloadedMap, xmlDOMRegistry, assetManager,configuration);
            downloader.downloadRemoteResources();
        }
    }

}
