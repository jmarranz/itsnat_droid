package org.itsnat.droid.impl.browser;

import org.itsnat.droid.ClientErrorMode;
import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidServerResponseException;
import org.itsnat.droid.OnHttpRequestErrorListener;
import org.itsnat.droid.OnHttpRequestListener;
import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocImpl;
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

    public GenericHttpClientAsyncTask(GenericHttpClientImpl parent, String method, String url, List<NameValue> paramList, OnHttpRequestListener listener, OnHttpRequestErrorListener errorListener, String overrideMime)
    {
        this.parent = parent;
        this.method = method;
        this.url = url;
        this.httpRequestData = new HttpRequestData(parent);
        this.paramList = new ArrayList<NameValue>(paramList); // hace una copia, los NameValue son de sólo lectura por lo que no hay problema de compartirlos en hilos
        this.listener = listener;
        this.errorListener = errorListener;
        this.overrideMime = overrideMime;
        this.errorMode = parent.getErrorMode();
    }

    @Override
    protected HttpRequestResultOKImpl executeInBackground() throws Exception
    {
        return HttpUtil.httpAction(method,url, httpRequestData, paramList,overrideMime);
    }

    @Override
    protected void onFinishOk(HttpRequestResultOKImpl result)
    {
        try
        {
            parent.processResult(result,listener);
        }
        catch(Exception ex)
        {
            if (errorListener != null)
            {
                HttpRequestResult resultError = (ex instanceof ItsNatDroidServerResponseException) ? ((ItsNatDroidServerResponseException)ex).getHttpRequestResult() : result;
                errorListener.onError(parent.getPageImpl(), ex, resultError);
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
        HttpRequestResult result = (ex instanceof ItsNatDroidServerResponseException) ?  ((ItsNatDroidServerResponseException)ex).getHttpRequestResult() : null;

        ItsNatDroidException exFinal = GenericHttpClientBaseImpl.convertException(ex);

        if (errorListener != null)
        {
            errorListener.onError(parent.getPageImpl(),exFinal, result);
        }
        else
        {
            if (errorMode != ClientErrorMode.NOT_CATCH_ERRORS)
            {
                // Error del servidor, lo normal es que haya lanzado una excepción
                ItsNatDocImpl itsNatDoc = parent.getItsNatDocImpl();
                itsNatDoc.showErrorMessage(true, result,exFinal, errorMode);
            }
            else throw exFinal;
        }
    }
}

