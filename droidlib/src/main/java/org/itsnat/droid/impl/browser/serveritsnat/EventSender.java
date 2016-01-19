package org.itsnat.droid.impl.browser.serveritsnat;

import android.content.res.AssetManager;
import android.os.AsyncTask;

import org.itsnat.droid.ClientErrorMode;
import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidServerResponseException;
import org.itsnat.droid.OnEventErrorListener;
import org.itsnat.droid.impl.browser.HttpRequestData;
import org.itsnat.droid.impl.browser.HttpResourceDownloader;
import org.itsnat.droid.impl.browser.HttpUtil;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.browser.serveritsnat.event.EventGenericImpl;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.util.NameValue;
import org.itsnat.droid.impl.util.NamespaceUtil;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedList;
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

    public ItsNatDocItsNatImpl getItsNatDocItsNatImpl()
    {
        return evtManager.getItsNatDocItsNatImpl();
    }

    public void requestSync(EventGenericImpl evt, String servletPath, List<NameValue> paramList, long timeout)
    {
        ItsNatDocItsNatImpl itsNatDoc = getItsNatDocItsNatImpl();
        PageImpl page = itsNatDoc.getPageImpl();

        HttpRequestData httpRequestData = new HttpRequestData(page);
        int timeoutInt = (int) timeout;
        if (timeoutInt < 0)
            timeoutInt = Integer.MAX_VALUE;
        httpRequestData.setReadTimeout(timeoutInt);

        HttpRequestResultOKBeanshellImpl result = null;
        try
        {
            result = executeInBackground(this, servletPath, httpRequestData, paramList);
        }
        catch (Exception ex)
        {
            int errorMode = getItsNatDocItsNatImpl().getClientErrorMode();
            onFinishError(this, ex, evt, errorMode);

            return; // No podemos continuar
        }

        onFinishOk(this, evt, result);
    }

    public void requestAsync(EventGenericImpl evt, String servletPath, List<NameValue> paramList, long timeout)
    {
        HttpPostEventAsyncTask task = new HttpPostEventAsyncTask(this, evt, servletPath, paramList, timeout);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); // Con execute() a secas se ejecuta en un "pool" de un sólo hilo sin verdadero paralelismo
    }

    public static HttpRequestResultOKBeanshellImpl executeInBackground(EventSender eventSender, String servletPath, HttpRequestData httpRequestData, List<NameValue> paramList) throws Exception
    {
        // Ejecutado en multihilo en el caso async
        HttpRequestResultOKBeanshellImpl result = (HttpRequestResultOKBeanshellImpl)HttpUtil.httpPost(servletPath, httpRequestData, paramList, null);

        PageItsNatImpl pageItsNat = eventSender.getItsNatDocItsNatImpl().getPageItsNatImpl();

        XMLDOMLayoutPage xmldomLayoutPage = pageItsNat.getInflatedLayoutPageImpl().getXMLDOMLayoutPage();

        LinkedList<DOMAttrRemote> attrRemoteListBSParsed = xmldomLayoutPage.parseBSRemoteAttribs(result.getResponseText());

        if (attrRemoteListBSParsed != null)
        {
            downloadResources(attrRemoteListBSParsed, httpRequestData, pageItsNat);

            result.setAttrRemoteListBSParsed(attrRemoteListBSParsed);
        }

        return result;
    }

    public static void downloadResources(LinkedList<DOMAttrRemote> attrRemoteList,HttpRequestData httpRequestData,PageItsNatImpl pageItsNat) throws Exception
    {
        // llena los elementos de DOMAttrRemote attrRemoteList con el recurso descargado que le corresponde

        String pageURLBase = pageItsNat.getPageURLBase();
        XMLDOMRegistry xmlDOMRegistry = pageItsNat.getItsNatDroidBrowserImpl().getItsNatDroidImpl().getXMLDOMRegistry();
        AssetManager assetManager = pageItsNat.getContext().getResources().getAssets();
        HttpResourceDownloader resDownloader = new HttpResourceDownloader(pageURLBase, httpRequestData, xmlDOMRegistry, assetManager);
        resDownloader.downloadResources(attrRemoteList);
    }

    public static void onFinishOk(EventSender eventSender, EventGenericImpl evt, HttpRequestResultOKBeanshellImpl result)
    {
        try
        {
            eventSender.processResult(evt, result, true);
        }
        catch (Exception ex)
        {
            PageImpl page = eventSender.getEventManager().getItsNatDocItsNatImpl().getPageImpl();
            OnEventErrorListener errorListener = page.getOnEventErrorListener();
            if (errorListener != null)
            {
                HttpRequestResult resultError = (ex instanceof ItsNatDroidServerResponseException) ? ((ItsNatDroidServerResponseException) ex).getHttpRequestResult() : result;
                errorListener.onError(page, evt, ex, resultError);
            }
            else
            {
                if (ex instanceof ItsNatDroidException) throw (ItsNatDroidException) ex;
                else throw new ItsNatDroidException(ex);
            }
        }
    }


    public static void onFinishError(EventSender eventSender, Exception ex, EventGenericImpl evt, int errorMode)
    {
        HttpRequestResult result = (ex instanceof ItsNatDroidServerResponseException) ? ((ItsNatDroidServerResponseException) ex).getHttpRequestResult() : null;

        ItsNatDroidException exFinal = eventSender.convertExceptionAndFireEventMonitors(evt, ex);

        PageImpl page = eventSender.getEventManager().getItsNatDocItsNatImpl().getPageImpl();
        OnEventErrorListener errorListener = page.getOnEventErrorListener();
        if (errorListener != null)
        {
            errorListener.onError(page, evt, exFinal, result);
        }
        else
        {
            if (errorMode != ClientErrorMode.NOT_CATCH_ERRORS)
            {
                // Error del servidor, lo normal es que haya lanzado una excepción
                ItsNatDocItsNatImpl itsNatDoc = eventSender.getItsNatDocItsNatImpl();
                itsNatDoc.showErrorMessage(true, result, exFinal, errorMode);
            }
            else throw exFinal;
        }

    }



    public void processResult(EventGenericImpl evt,HttpRequestResultOKBeanshellImpl result,boolean async)
    {
        ItsNatDocItsNatImpl itsNatDoc = getItsNatDocItsNatImpl();
        itsNatDoc.fireEventMonitors(false,false,evt);

        String responseText = result.getResponseText();
        itsNatDoc.evalEventResultScript(responseText, evt, result.getAttrRemoteListBSParsed());

        if (async) evtManager.returnedEvent(evt);
    }

    public ItsNatDroidException convertExceptionAndFireEventMonitors(EventGenericImpl evt, Exception ex)
    {
        ItsNatDocItsNatImpl itsNatDoc = getItsNatDocItsNatImpl();

        if (ex instanceof ItsNatDroidException && ex.getCause() instanceof SocketTimeoutException)
        {
            // Esperamos este error en el caso de timeout y lo tratamos de forma singular en fireEventMonitors (el segundo param a true)
            itsNatDoc.fireEventMonitors(false, true, evt);
        }
        else
        {
            itsNatDoc.fireEventMonitors(false, false, evt);
        }

        if (ex instanceof ItsNatDroidException) return (ItsNatDroidException)ex;
        else return new ItsNatDroidException(ex);
    }


}
