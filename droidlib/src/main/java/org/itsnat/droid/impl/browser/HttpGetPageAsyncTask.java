package org.itsnat.droid.impl.browser;

import android.content.res.AssetManager;

import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidServerResponseException;
import org.itsnat.droid.OnPageLoadErrorListener;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;

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

    public HttpGetPageAsyncTask(PageRequestImpl pageRequest, String url)
    {
        ItsNatDroidBrowserImpl browser = pageRequest.getItsNatDroidBrowserImpl();
        ItsNatDroidImpl itsNatDroid = browser.getItsNatDroidImpl();

        // Hay que tener en cuenta que estos objetos se acceden en multihilo
        this.pageRequest = pageRequest;
        this.url = url;
        this.pageURLBase = pageRequest.getURLBase();
        this.xmlDOMRegistry = itsNatDroid.getXMLDOMRegistry();
        this.assetManager = pageRequest.getContext().getAssets();
        this.httpRequestData = new HttpRequestData(pageRequest);
    }

    protected PageRequestResult executeInBackground() throws Exception
    {
        HttpRequestResultOKImpl result = HttpUtil.httpGet(url, httpRequestData,null, null);

        PageRequestResult pageReqResult = PageRequestImpl.processHttpRequestResult(result,pageURLBase, httpRequestData,xmlDOMRegistry,assetManager);
        return pageReqResult;
    }


    @Override
    protected void onFinishOk(PageRequestResult result)
    {
        try
        {
            pageRequest.processResponse(result);
        }
        catch(Exception ex)
        {
            OnPageLoadErrorListener errorListener = pageRequest.getOnPageLoadErrorListener();
            if (errorListener != null)
            {
                HttpRequestResult resultError = (ex instanceof ItsNatDroidServerResponseException) ? ((ItsNatDroidServerResponseException)ex).getHttpRequestResult() : result.getHttpRequestResultOKImpl();
                errorListener.onError(ex, pageRequest,resultError); // Para poder recogerla desde fuera
            }
            else
            {
                if (ex instanceof ItsNatDroidException) throw (ItsNatDroidException)ex;
                else throw new ItsNatDroidException(ex);
            }
        }
    }

    @Override
    protected void onFinishError(Exception ex)
    {
        HttpRequestResult result = (ex instanceof ItsNatDroidServerResponseException) ? ((ItsNatDroidServerResponseException)ex).getHttpRequestResult() : null;

        ItsNatDroidException exFinal = (ex instanceof ItsNatDroidException) ? (ItsNatDroidException)ex : new ItsNatDroidException(ex);

        OnPageLoadErrorListener errorListener = pageRequest.getOnPageLoadErrorListener();
        if (errorListener != null)
        {
            errorListener.onError(exFinal, pageRequest,result);
        }
        else
        {
            // No se ha cargado la página en este contexto, no tenemos por ejemplo un errorMode
            throw exFinal;
        }
    }
}
