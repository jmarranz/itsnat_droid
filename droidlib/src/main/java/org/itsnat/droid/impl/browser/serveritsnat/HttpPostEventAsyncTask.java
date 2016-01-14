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
        return EventSender.executeInBackground(eventSender,servletPath, httpRequestData, paramList);
    }

    @Override
    protected void onFinishOk(HttpRequestResultOKImpl result)
    {
        EventSender.onFinishOk(eventSender, evt, result);
    }

    @Override
    protected void onFinishError(Exception ex)
    {
        EventSender.onFinishError(eventSender,ex,evt,errorMode);
    }
}

