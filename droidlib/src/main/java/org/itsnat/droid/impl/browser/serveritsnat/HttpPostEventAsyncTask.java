package org.itsnat.droid.impl.browser.serveritsnat;

import org.itsnat.droid.ItsNatDroidException;
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

    public HttpPostEventAsyncTask(EventSender eventSender, EventGenericImpl evt, String servletPath,
            List<NameValue> paramList,long timeout)
    {
        PageImpl page = eventSender.getItsNatDocImpl().getPageImpl();

        // Hay que tener en cuenta que estos objetos se acceden en multihilo
        this.eventSender = eventSender;
        this.evt = evt;
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
            OnEventErrorListener errorListener = eventSender.getEventManager().getItsNatDocImpl().getPageImpl().getOnEventErrorListener();
            if (errorListener != null)
            {
                errorListener.onError(ex, evt);
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
        ItsNatDroidException exFinal = eventSender.convertException(evt, ex);

        OnEventErrorListener errorListener = eventSender.getEventManager().getItsNatDocImpl().getPageImpl().getOnEventErrorListener();
        if (errorListener != null)
        {
            errorListener.onError(exFinal, evt);
        }
        else
        {
            throw exFinal;
        }

    }
}

