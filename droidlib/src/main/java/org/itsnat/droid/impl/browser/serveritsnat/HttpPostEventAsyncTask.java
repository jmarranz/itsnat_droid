package org.itsnat.droid.impl.browser.serveritsnat;

import android.content.res.AssetManager;

import org.itsnat.droid.impl.browser.HttpRequestData;
import org.itsnat.droid.impl.browser.ProcessingAsyncTask;
import org.itsnat.droid.impl.browser.serveritsnat.event.EventGenericImpl;
import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.util.NameValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 4/06/14.
 */
public class HttpPostEventAsyncTask extends ProcessingAsyncTask<HttpRequestResultOKBeanshellImpl>
{
    protected final EventSender eventSender;
    protected final EventGenericImpl evt;
    protected final String servletPath;
    protected final HttpRequestData httpRequestData;
    protected final List<NameValue> paramList;
    protected final XMLDOMRegistry xmlDOMRegistry;
    protected final AssetManager assetManager;
    protected final Map<String,ParsedResource> urlResDownloadedMap;

    public HttpPostEventAsyncTask(EventSender eventSender, EventGenericImpl evt, String servletPath,
                        List<NameValue> paramList,HttpRequestData httpRequestData,
                        Map<String,ParsedResource> urlResDownloadedMap,XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager)
    {
        // Hay que tener en cuenta que estos objetos se acceden en multihilo
        this.eventSender = eventSender;
        this.evt = evt;
        this.servletPath = servletPath;
        this.httpRequestData = httpRequestData;
        this.paramList = new ArrayList<NameValue>(paramList); // hace una copia, los NameValuePair son de sólo lectura por lo que no hay problema compartirlos en hilos
        this.urlResDownloadedMap = urlResDownloadedMap;
        this.xmlDOMRegistry = xmlDOMRegistry;
        this.assetManager = assetManager;
    }

    @Override
    protected HttpRequestResultOKBeanshellImpl executeInBackground() throws Exception
    {
        return EventSender.executeInBackground(eventSender,servletPath, httpRequestData, paramList,urlResDownloadedMap,xmlDOMRegistry,assetManager);
    }

    @Override
    protected void onFinishOk(HttpRequestResultOKBeanshellImpl result)
    {
        EventSender.onFinishOk(eventSender, evt, result);
    }

    @Override
    protected void onFinishError(Exception ex)
    {
        EventSender.onFinishError(eventSender,ex,evt);
    }
}

