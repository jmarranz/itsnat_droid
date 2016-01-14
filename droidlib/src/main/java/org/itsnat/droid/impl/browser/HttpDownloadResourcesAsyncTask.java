package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;

import org.itsnat.droid.ClientErrorMode;
import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidServerResponseException;
import org.itsnat.droid.OnHttpRequestErrorListener;
import org.itsnat.droid.OnHttpRequestListener;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jmarranz on 4/06/14.
 */
public class HttpDownloadResourcesAsyncTask extends ProcessingAsyncTask<List<HttpRequestResultOKImpl>>
{
    protected List<DOMAttrRemote> attrRemoteList;
    protected DownloadResourcesHttpClient parent;
    protected String method;
    protected String pageURLBase;
    protected HttpRequestData httpRequestData;
    protected OnHttpRequestListener httpRequestListener;
    protected OnHttpRequestErrorListener errorListener;
    protected int errorMode;
    protected XMLDOMRegistry xmlDOMRegistry;
    protected AssetManager assetManager;

    public HttpDownloadResourcesAsyncTask(List<DOMAttrRemote> attrRemoteList,DownloadResourcesHttpClient parent, String method, String pageURLBase, OnHttpRequestListener httpRequestListener, OnHttpRequestErrorListener errorListener,int errorMode, AssetManager assetManager)
    {
        PageImpl page = parent.getPageImpl();

        this.attrRemoteList = attrRemoteList;
        this.parent = parent;
        this.method = method;
        this.pageURLBase = pageURLBase;
        this.httpRequestData = new HttpRequestData(page);
        this.httpRequestListener = httpRequestListener;
        this.errorListener = errorListener;
        this.errorMode = errorMode;
        this.xmlDOMRegistry = page.getItsNatDroidBrowserImpl().getItsNatDroidImpl().getXMLDOMRegistry();
        this.assetManager = assetManager;
    }

    protected List<HttpRequestResultOKImpl> executeInBackground() throws Exception
    {
        return DownloadResourcesHttpClient.executeInBackground(parent,attrRemoteList,pageURLBase,httpRequestData,xmlDOMRegistry,assetManager);
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

