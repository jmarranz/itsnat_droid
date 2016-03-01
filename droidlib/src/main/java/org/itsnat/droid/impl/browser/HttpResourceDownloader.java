package org.itsnat.droid.impl.browser;

import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.dom.ParsedResourceXMLDOM;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    protected final XMLDOMParserContext xmlDOMParserContext;

    public HttpResourceDownloader(String pageURLBase,HttpRequestData httpRequestData, String itsNatServerVersion,Map<String,ParsedResource> urlResDownloadedMap,XMLDOMParserContext xmlDOMParserContext)
    {
        this.pageURLBase = pageURLBase;
        this.httpRequestData = httpRequestData;
        this.itsNatServerVersion = itsNatServerVersion;
        this.urlResDownloadedMap = urlResDownloadedMap;
        this.xmlDOMParserContext = xmlDOMParserContext;
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
        DOMAttrRemote[] attrRemoteArray = attrRemoteList.toArray(new DOMAttrRemote[len]);
        final Thread[] threadArray = new Thread[len];
        final Exception[] exList = new Exception[len];

        int cores;
        try { cores = Runtime.getRuntime().availableProcessors(); } // Un Nexus 4 devuelve 4 processors = cores
        catch(Exception ex) { cores = 8; }
        if (cores < 1) cores = 8; // Pase lo que pase ponemos un valor coherente

        int threadsPerCore = 5;  // Para evitar demasiados hilos concurrentes a la vez, sobre t_odo por el tema de la memoria
        int maxThreads = cores * threadsPerCore;

        int threadConcurrentGroups = len / maxThreads + 1; // El +1 es para redondear hacia arriba, el if (j2 >= len) j2 = len; ya ajustará el valor exacto

        int j1;
        int j2;
        for(int i = 0; i < threadConcurrentGroups; i++)
        {
            j1 = i * maxThreads;
            j2 = j1 + maxThreads;

            if (j1 >= len) break; // Sobra pero para que quede claro
            if (j2 >= len) j2 = len;

            final boolean[] stop = new boolean[1];
            for (int k = j1; k < j2; k++)
            {
                DOMAttrRemote attr = attrRemoteArray[k];
                Runnable task = createTaskToDownloadResource(attr, stop, k, resultList, exList);
                Thread thread = new Thread(task);
                thread.start();  // Alternativa: ThreadPoolExecutor, aunque realmente el que asigna cores a threads es Linux a bajo nivel
                threadArray[k] = thread;
            }

            for (int k = j1; k < j2; k++)
            {
                threadArray[k].join();
            }

            for (int k = j1; k < j2; k++)
            {
                if (exList[k] != null) throw exList[k]; // La primera que encontremos (es raro que haya más de una)
            }
        }

    }

    private Runnable createTaskToDownloadResource(final DOMAttrRemote attr, final boolean[] stop, final int i, final List<HttpRequestResultOKImpl> resultList, final Exception[] exList) throws Exception
    {
        Runnable task = new Runnable()
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

        return task;
    }

    private void processHttpRequestResultResource(String absURL,DOMAttrRemote attr, HttpRequestResultOKImpl resultRes, List<HttpRequestResultOKImpl> resultList) throws Exception
    {
        // Método llamado en multihilo

        resultList.add(resultRes);

        ParsedResource resource = XMLDOMParser.parseDOMAttrRemote(attr, resultRes,xmlDOMParserContext);
        synchronized(urlResDownloadedMap)
        {
            urlResDownloadedMap.put(absURL,resource); // No pasa nada si dos hilos con el mismo absURL-resource hacen put seguidos
        }

        if (resource instanceof ParsedResourceXMLDOM)
        {
            XMLDOM xmlDOM = ((ParsedResourceXMLDOM)resource).getXMLDOM();
            String absURLContainer = HttpUtil.composeAbsoluteURL(attr.getLocation(), pageURLBase);
            String pageURLBaseContainer = HttpUtil.getBasePathOfURL(absURLContainer);
            XMLDOMDownloader downloader = XMLDOMDownloader.createXMLDOMDownloader(xmlDOM,pageURLBaseContainer, httpRequestData, itsNatServerVersion,urlResDownloadedMap,xmlDOMParserContext);
            downloader.downloadRemoteResources();
        }
    }

}
