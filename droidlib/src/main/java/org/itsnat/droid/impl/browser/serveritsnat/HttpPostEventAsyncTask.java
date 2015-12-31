package org.itsnat.droid.impl.browser.serveritsnat;

import org.itsnat.droid.ClientErrorMode;
import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidServerResponseException;
import org.itsnat.droid.OnEventErrorListener;
import org.itsnat.droid.impl.browser.HttpRequestData;
import org.itsnat.droid.impl.browser.HttpRequestResultOKImpl;
import org.itsnat.droid.impl.browser.HttpUtil;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.browser.ProcessingAsyncTask;
import org.itsnat.droid.impl.browser.serveritsnat.event.EventGenericImpl;
import org.itsnat.droid.impl.util.NameValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmarranz on 4/06/14.
 */
public class HttpPostEventAsyncTask extends ProcessingAsyncTask<HttpRequestResultOKImpl>
{
    protected EventSender eventSender;
    protected EventGenericImpl evt;
    protected String servletPath;
    protected HttpRequestData httpRequestData;
    protected List<NameValue> paramList;
    protected int errorMode;

    public HttpPostEventAsyncTask(EventSender eventSender, EventGenericImpl evt, String servletPath,
            List<NameValue> paramList,long timeout)
    {
        PageImpl page = eventSender.getItsNatDocItsNatImpl().getPageImpl();

        // Hay que tener en cuenta que estos objetos se acceden en multihilo
        this.eventSender = eventSender;
        this.evt = evt;
        this.errorMode = eventSender.getItsNatDocItsNatImpl().getClientErrorMode();
        this.servletPath = servletPath;
        this.httpRequestData = new HttpRequestData(page);
        int timeoutInt = (int)timeout;
        if (timeoutInt < 0)
            timeoutInt = Integer.MAX_VALUE;
        httpRequestData.setReadTimeout(timeoutInt);
        this.paramList = new ArrayList<NameValue>(paramList); // hace una copia, los NameValuePair son de sólo lectura por lo que no hay problema compartirlos en hilos
    }

    @Override
    protected HttpRequestResultOKImpl executeInBackground() throws Exception
    {
        return HttpUtil.httpPost(servletPath, httpRequestData, paramList,null);
    }

    @Override
    protected void onFinishOk(HttpRequestResultOKImpl result)
    {
        try
        {
            eventSender.processResult(evt, result, true);
        }
        catch(Exception ex)
        {
            OnEventErrorListener errorListener = eventSender.getEventManager().getItsNatDocItsNatImpl().getPageImpl().getOnEventErrorListener();
            if (errorListener != null)
            {
                HttpRequestResult resultError = (ex instanceof ItsNatDroidServerResponseException) ? ((ItsNatDroidServerResponseException)ex).getHttpRequestResult() : result;
                errorListener.onError(ex, evt,resultError);
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

        ItsNatDroidException exFinal = eventSender.convertExceptionAndFireEventMonitors(evt, ex);

        OnEventErrorListener errorListener = eventSender.getEventManager().getItsNatDocItsNatImpl().getPageImpl().getOnEventErrorListener();
        if (errorListener != null)
        {
            errorListener.onError(exFinal, evt,result);
        }
        else
        {
            if (errorMode != ClientErrorMode.NOT_CATCH_ERRORS)
            {
                // Error del servidor, lo normal es que haya lanzado una excepción
                ItsNatDocItsNatImpl itsNatDoc = eventSender.getItsNatDocItsNatImpl();
                itsNatDoc.showErrorMessage(true, result,exFinal, errorMode);
            }
            else throw exFinal;
        }

    }
}

