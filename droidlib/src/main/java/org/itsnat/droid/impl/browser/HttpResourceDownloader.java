package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.dom.ParsedResourceXMLDOM;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jmarranz on 12/11/14.
 */
public class HttpResourceDownloader
{
    protected final String pageURLBase;
    protected final HttpRequestData httpRequestData;
    protected final XMLDOMRegistry xmlDOMRegistry;
    protected final AssetManager assetManager;

    public HttpResourceDownloader(String pageURLBase,HttpRequestData httpRequestData,XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager)
    {
        this.pageURLBase = pageURLBase;
        this.httpRequestData = httpRequestData;
        this.xmlDOMRegistry = xmlDOMRegistry;
        this.assetManager = assetManager;
    }

    public List<HttpRequestResultOKImpl> downloadResources(List<DOMAttrRemote> attrRemoteList) throws Exception
    {
        List<HttpRequestResultOKImpl> resultList = Collections.synchronizedList(new ArrayList<HttpRequestResultOKImpl>()); // Necesario synchronizedList(), pues se comparte entre hilos
        downloadResources(pageURLBase,attrRemoteList,resultList);
        return resultList;
    }

    private void downloadResources(String pageURLBase,List<DOMAttrRemote> attrRemoteList,List<HttpRequestResultOKImpl> resultList) throws Exception
    {
        int len = attrRemoteList.size();
        final Thread[] threadArray = new Thread[len];
        final Exception[] exList = new Exception[len];

        {
            int i = 0;
            final boolean[] stop = new boolean[1];
            for (DOMAttrRemote attr : attrRemoteList)
            {
                Thread thread = downloadResource(pageURLBase,attr, stop, i,resultList, exList);
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

    private Thread downloadResource(final String pageURLBase,final DOMAttrRemote attr, final boolean[] stop, final int i,
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
                    String url = HttpUtil.composeAbsoluteURL(attr.getLocation(), pageURLBase);
                    HttpRequestResultOKImpl resultResource = HttpUtil.httpGet(url, httpRequestData, null, resourceMime);

                    processHttpRequestResultResource(url, attr, resultResource, resultList);
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

    private void processHttpRequestResultResource(String urlBase, DOMAttrRemote attr, HttpRequestResultOKImpl resultRes, List<HttpRequestResultOKImpl> resultList) throws Exception
    {
        // Método llamado en multihilo

        if (resultList != null) resultList.add(resultRes);

        ParsedResource resource = XMLDOMParser.parseDOMAttrRemote(attr, resultRes, xmlDOMRegistry, assetManager);
        if (resource instanceof ParsedResourceXMLDOM)
        {
            XMLDOM xmlDOM = ((ParsedResourceXMLDOM)resource).getXMLDOM();
            LinkedList<DOMAttrRemote> attrRemoteList = xmlDOM.getDOMAttrRemoteList();
            if (attrRemoteList != null)
                downloadResources(urlBase, attrRemoteList, resultList);
        }
    }

}
