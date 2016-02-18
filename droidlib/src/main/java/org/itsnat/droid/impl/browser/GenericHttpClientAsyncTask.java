package org.itsnat.droid.impl.browser;

import org.itsnat.droid.OnHttpRequestErrorListener;
import org.itsnat.droid.OnHttpRequestListener;
import org.itsnat.droid.impl.util.NameValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmarranz on 4/06/14.
 */
public class GenericHttpClientAsyncTask extends ProcessingAsyncTask<HttpRequestResultOKImpl>
{
    protected GenericHttpClientImpl parent;
    protected String method;
    protected String url;
    protected HttpRequestData httpRequestData;
    protected List<NameValue> paramList;
    protected OnHttpRequestListener listener;
    protected OnHttpRequestErrorListener errorListener;
    protected String overrideMime;
    protected int errorMode;

    public GenericHttpClientAsyncTask(GenericHttpClientImpl parent, String method, String url,HttpRequestData httpRequestData, List<NameValue> paramList, OnHttpRequestListener listener, OnHttpRequestErrorListener errorListener, String overrideMime)
    {
        this.parent = parent;
        this.method = method;
        this.url = url;
        this.httpRequestData = httpRequestData;
        this.paramList = paramList;
        this.listener = listener;
        this.errorListener = errorListener;
        this.overrideMime = overrideMime;
        this.errorMode = parent.getClientErrorMode();
    }

    @Override
    protected HttpRequestResultOKImpl executeInBackground() throws Exception
    {
        return GenericHttpClientImpl.executeInBackground(parent,method,url, httpRequestData, paramList,overrideMime);
    }

    @Override
    protected void onFinishOk(HttpRequestResultOKImpl result)
    {
        GenericHttpClientImpl.onFinishOk(parent, result, listener, errorListener);
    }

    @Override
    protected void onFinishError(Exception ex)
    {
        GenericHttpClientImpl.onFinishError(parent,ex, listener,errorListener,errorMode);
    }
}

