package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

import java.util.Locale;
import java.util.Map;

/**
 * Created by jmarranz on 4/06/14.
 */
public class HttpGetPageAsyncTask extends ProcessingAsyncTask<PageRequestResult>
{
    // No hay problemas de hilos, únicamente se pasa a un objeto resultado y dicho objeto no hace nada con él durante la ejecución del hilo
    protected final PageRequestImpl pageRequest;
    protected final String url;
    protected final String pageURLBase;
    protected final HttpRequestData httpRequestData;
    protected final XMLDOMRegistry xmlDOMRegistry;
    protected final AssetManager assetManager;
    protected final Configuration configuration;
    protected final Map<String,ParsedResource> urlResDownloadedMap;

    public HttpGetPageAsyncTask(PageRequestImpl pageRequest, String url,String pageURLBase,HttpRequestData httpRequestData,Map<String,ParsedResource> urlResDownloadedMap,XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager,Configuration configuration)
    {
         // Hay que tener en cuenta que estos objetos se acceden en multihilo
        this.pageRequest = pageRequest;
        this.url = url;
        this.pageURLBase = pageURLBase;
        this.urlResDownloadedMap = urlResDownloadedMap;
        this.xmlDOMRegistry = xmlDOMRegistry;
        this.assetManager = assetManager;
        this.configuration = configuration;
        this.httpRequestData = httpRequestData;
    }

    protected PageRequestResult executeInBackground() throws Exception
    {
        return PageRequestImpl.executeInBackground(url,pageURLBase,httpRequestData,urlResDownloadedMap,xmlDOMRegistry,assetManager,configuration);
    }


    @Override
    protected void onFinishOk(PageRequestResult result)
    {
        PageRequestImpl.onFinishOk(pageRequest,result);
    }

    @Override
    protected void onFinishError(Exception ex)
    {
        PageRequestImpl.onFinishError(pageRequest, ex);
    }
}
