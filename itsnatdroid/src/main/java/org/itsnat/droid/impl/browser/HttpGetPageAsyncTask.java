package org.itsnat.droid.impl.browser;

import android.content.Context;

import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

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
    protected final XMLDOMParserContext xmlDOMParserContext;
    protected final Context ctx;
    protected final Map<String,ParsedResource> urlResDownloadedMap;


    public HttpGetPageAsyncTask(PageRequestImpl pageRequest, String url,String pageURLBase,HttpRequestData httpRequestData,Map<String,ParsedResource> urlResDownloadedMap,XMLDOMParserContext xmlDOMParserContext,Context ctx)
    {
         // Hay que tener en cuenta que estos objetos se acceden en multihilo
        this.pageRequest = pageRequest;
        this.url = url;
        this.pageURLBase = pageURLBase;
        this.urlResDownloadedMap = urlResDownloadedMap;
        this.xmlDOMParserContext = xmlDOMParserContext;
        this.ctx = ctx;
        this.httpRequestData = httpRequestData;
    }

    protected PageRequestResult executeInBackground() throws Exception
    {
        return PageRequestImpl.executeInBackground(url,pageURLBase,httpRequestData,urlResDownloadedMap,xmlDOMParserContext);
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
