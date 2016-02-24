package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;
import android.content.res.Configuration;

import org.itsnat.droid.OnHttpRequestErrorListener;
import org.itsnat.droid.OnHttpRequestListener;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 4/06/14.
 */
public class HttpDownloadResourcesAsyncTask extends ProcessingAsyncTask<List<HttpRequestResultOKImpl>>
{
    protected final List<DOMAttrRemote> attrRemoteList;
    protected final DownloadResourcesHttpClient parent;
    protected final String pageURLBase;
    protected final HttpRequestData httpRequestData;
    protected final String itsNatServerVersion;
    protected final OnHttpRequestListener httpRequestListener;
    protected final OnHttpRequestErrorListener errorListener;
    protected final int errorMode;
    protected final XMLDOMParserContext xmlDOMParserContext;
    protected final Map<String,ParsedResource> urlResDownloadedMap;

    public HttpDownloadResourcesAsyncTask(List<DOMAttrRemote> attrRemoteList,DownloadResourcesHttpClient parent,String pageURLBase,HttpRequestData httpRequestData,
                                          OnHttpRequestListener httpRequestListener,OnHttpRequestErrorListener errorListener,int errorMode,
                                          String itsNatServerVersion,Map<String,ParsedResource> urlResDownloadedMap,XMLDOMParserContext xmlDOMParserContext)
    {
        this.attrRemoteList = attrRemoteList;
        this.parent = parent;
        this.pageURLBase = pageURLBase;
        this.httpRequestData = httpRequestData;
        this.httpRequestListener = httpRequestListener;
        this.itsNatServerVersion = itsNatServerVersion;
        this.errorListener = errorListener;
        this.errorMode = errorMode;
        this.urlResDownloadedMap = urlResDownloadedMap;
        this.xmlDOMParserContext = xmlDOMParserContext;
    }

    protected List<HttpRequestResultOKImpl> executeInBackground() throws Exception
    {
        return DownloadResourcesHttpClient.executeInBackground(attrRemoteList,pageURLBase,httpRequestData,itsNatServerVersion,urlResDownloadedMap,xmlDOMParserContext);
    }

    @Override
    protected void onFinishOk(List<HttpRequestResultOKImpl> resultList)
    {
        DownloadResourcesHttpClient.onFinishOk(parent,resultList, httpRequestListener, errorListener);
    }

    @Override
    protected void onFinishError(Exception ex)
    {
        DownloadResourcesHttpClient.onFinishError(parent, ex, errorListener, errorMode);
    }
}

