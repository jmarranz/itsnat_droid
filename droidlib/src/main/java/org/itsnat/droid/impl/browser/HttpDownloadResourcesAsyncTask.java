package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;

import org.itsnat.droid.OnHttpRequestErrorListener;
import org.itsnat.droid.OnHttpRequestListener;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by jmarranz on 4/06/14.
 */
public class HttpDownloadResourcesAsyncTask extends ProcessingAsyncTask<List<HttpRequestResultOKImpl>>
{
    protected final List<DOMAttrRemote> attrRemoteList;
    protected final DownloadResourcesHttpClient parent;
    protected final String method;
    protected final String pageURLBase;
    protected final HttpRequestData httpRequestData;
    protected final String itsNatServerVersion;
    protected final OnHttpRequestListener httpRequestListener;
    protected final OnHttpRequestErrorListener errorListener;
    protected final int errorMode;
    protected final XMLDOMRegistry xmlDOMRegistry;
    protected final AssetManager assetManager;
    protected final Locale locale;
    protected final Map<String,ParsedResource> urlResDownloadedMap;

    public HttpDownloadResourcesAsyncTask(List<DOMAttrRemote> attrRemoteList,DownloadResourcesHttpClient parent, String method, String pageURLBase, OnHttpRequestListener httpRequestListener,
                                          OnHttpRequestErrorListener errorListener,int errorMode,Map<String,ParsedResource> urlResDownloadedMap,
                                          AssetManager assetManager,Locale locale)
    {
        PageImpl page = parent.getPageImpl();

        this.attrRemoteList = attrRemoteList;
        this.parent = parent;
        this.method = method;
        this.pageURLBase = pageURLBase;
        this.httpRequestData = new HttpRequestData(page);
        this.httpRequestListener = httpRequestListener;
        this.itsNatServerVersion = page.getItsNatServerVersion();
        this.errorListener = errorListener;
        this.errorMode = errorMode;
        this.urlResDownloadedMap = urlResDownloadedMap;
        this.xmlDOMRegistry = page.getItsNatDroidBrowserImpl().getItsNatDroidImpl().getXMLDOMRegistry();
        this.assetManager = assetManager;
        this.locale = locale;
    }

    protected List<HttpRequestResultOKImpl> executeInBackground() throws Exception
    {
        return DownloadResourcesHttpClient.executeInBackground(attrRemoteList,pageURLBase,httpRequestData,itsNatServerVersion,urlResDownloadedMap,xmlDOMRegistry,assetManager,locale);
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

