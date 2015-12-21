package org.itsnat.droid.impl.browser.serveritsnat;

import android.os.AsyncTask;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.HttpRequestData;
import org.itsnat.droid.impl.browser.HttpRequestResultOKImpl;
import org.itsnat.droid.impl.browser.HttpUtil;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.browser.serveritsnat.event.EventGenericImpl;
import org.itsnat.droid.impl.util.NameValue;

import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Created by jmarranz on 10/07/14.
 */
public class EventSender
{
    protected EventManager evtManager;

    public EventSender(EventManager evtManager)
    {
        this.evtManager = evtManager;
    }

    public EventManager getEventManager()
    {
        return evtManager;
    }

    public ItsNatDocImpl getItsNatDocImpl()
    {
        return evtManager.getItsNatDocImpl();
    }

    public void requestSync(EventGenericImpl evt, String servletPath, List<NameValue> paramList, long timeout)
    {
        ItsNatDocImpl itsNatDoc = getItsNatDocImpl();
        PageImpl page = itsNatDoc.getPageImpl();

        HttpRequestData httpRequestData = new HttpRequestData(page);
        int timeoutInt = (int)timeout;
        if (timeoutInt < 0)
            timeoutInt = Integer.MAX_VALUE;
        httpRequestData.setReadTimeout(timeoutInt);

        HttpRequestResultOKImpl result = null;
        try
        {
            result = HttpUtil.httpPost(servletPath,httpRequestData, paramList,null);
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            ItsNatDroidException exFinal = convertException(evt, ex);
            throw exFinal;

            // No usamos aquí el OnEventErrorListener porque la excepción es capturada por un catch anterior que sí lo hace
        }

        processResult(evt,result,false);
    }

    public void requestAsync(EventGenericImpl evt, String servletPath, List<NameValue> paramList, long timeout)
    {
        HttpPostEventAsyncTask task = new HttpPostEventAsyncTask(this, evt, servletPath, paramList,timeout);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); // Con execute() a secas se ejecuta en un "pool" de un sólo hilo sin verdadero paralelismo
    }

    public void processResult(EventGenericImpl evt,HttpRequestResultOKImpl result,boolean async)
    {
        ItsNatDocImpl itsNatDoc = getItsNatDocImpl();
        itsNatDoc.fireEventMonitors(false,false,evt);

        String responseText = result.getResponseText();
        itsNatDoc.eval(responseText);

        if (async) evtManager.returnedEvent(evt);
    }

    public ItsNatDroidException convertException(EventGenericImpl evt, Exception ex)
    {
        ItsNatDocImpl itsNatDoc = getItsNatDocImpl();

        if (ex instanceof SocketTimeoutException) // Esperamos este error en el caso de timeout
        {
            itsNatDoc.fireEventMonitors(false, true, evt);

            return new ItsNatDroidException(ex);
        }
        else
        {
            itsNatDoc.fireEventMonitors(false, false, evt);

            if (ex instanceof ItsNatDroidException) return (ItsNatDroidException)ex;
            else return new ItsNatDroidException(ex);
        }
    }


}
