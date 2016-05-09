package org.itsnat.droid.impl.browser;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.DisplayMetrics;

import org.apache.http.params.HttpParams;
import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.ClientErrorMode;
import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidServerResponseException;
import org.itsnat.droid.OnPageLoadErrorListener;
import org.itsnat.droid.OnPageLoadListener;
import org.itsnat.droid.OnScriptErrorListener;
import org.itsnat.droid.PageRequest;
import org.itsnat.droid.impl.browser.serveritsnat.PageItsNatImpl;
import org.itsnat.droid.impl.browser.serveritsnat.XMLDOMLayoutPageItsNatDownloader;
import org.itsnat.droid.impl.browser.servernotitsnat.PageNotItsNatImpl;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.dom.ParsedResourceXMLDOM;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageItsNat;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.domparser.layout.XMLDOMLayoutParser;
import org.itsnat.droid.impl.util.MimeUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 5/06/14.
 */
public class PageRequestImpl implements PageRequest
{
    protected ItsNatDroidBrowserImpl browser;
    protected Context ctx;
    protected RequestPropertyMap requestPropertyMap;
    protected HttpParams httpParams;
    protected int connectTimeout;
    protected int readTimeout;
    protected int bitmapDensityReference = DisplayMetrics.DENSITY_XHIGH;
    protected int errorMode = ClientErrorMode.SHOW_SERVER_AND_CLIENT_ERRORS;
    protected OnPageLoadListener pageLoadListener;
    protected OnPageLoadErrorListener pageLoadErrorListener;
    protected OnScriptErrorListener scriptErrorListener;
    protected AttrResourceInflaterListener attrResourcesInflaterListener;
    protected boolean sync = false;
    protected String url;
    protected String pageURLBase;


    public PageRequestImpl(ItsNatDroidBrowserImpl browser)
    {
        this.browser = browser;
        this.requestPropertyMap = browser.getRequestPropertyMap().copy();
        this.httpParams = browser.getHttpParams().copy();
        this.connectTimeout = browser.getConnectTimeout();
        this.readTimeout = browser.getReadTimeout();
    }

    public PageRequestImpl(PageRequestImpl origin) // clone/copy origin to "this"
    {
        this.browser = origin.browser;
        this.ctx = origin.ctx;
        this.requestPropertyMap = origin.requestPropertyMap.copy();
        this.httpParams = origin.getHttpParams().copy();
        this.connectTimeout = origin.connectTimeout;
        this.readTimeout = origin.readTimeout;
        this.bitmapDensityReference = origin.bitmapDensityReference;
        this.errorMode = origin.errorMode;
        this.pageLoadListener = origin.pageLoadListener;
        this.pageLoadErrorListener = origin.pageLoadErrorListener;
        this.scriptErrorListener = origin.getOnScriptErrorListener();
        this.attrResourcesInflaterListener = origin.attrResourcesInflaterListener;
        this.sync = origin.sync;
        this.url = origin.url;
        this.pageURLBase = origin.pageURLBase;
    }

    public ItsNatDroidBrowserImpl getItsNatDroidBrowserImpl()
    {
        return browser;
    }

    public Context getContext()
    {
        return ctx;
    }

    @Override
    public PageRequest setContext(Context ctx)
    {
        this.ctx = ctx;
        return this;
    }

    public int getBitmapDensityReference()
    {
        return bitmapDensityReference;
    }

    @Override
    public PageRequest setBitmapDensityReference(int bitmapDensityReference)
    {
        this.bitmapDensityReference = bitmapDensityReference;
        return this;
    }

    public int getClientErrorMode()
    {
        return errorMode;
    }

    @Override
    public PageRequest setClientErrorMode(int errorMode)
    {
        this.errorMode = errorMode;
        return this;
    }

    public OnPageLoadListener getOnPageLoadListener()
    {
        return pageLoadListener;
    }

    @Override
    public PageRequest setOnPageLoadListener(OnPageLoadListener pageListener)
    {
        this.pageLoadListener = pageListener;
        return this;
    }

    public OnPageLoadErrorListener getOnPageLoadErrorListener()
    {
        return pageLoadErrorListener;
    }

    @Override
    public PageRequest setOnPageLoadErrorListener(OnPageLoadErrorListener errorListener)
    {
        this.pageLoadErrorListener = errorListener;
        return this;
    }

    public OnScriptErrorListener getOnScriptErrorListener()
    {
        return scriptErrorListener;
    }

    @Override
    public PageRequest setOnScriptErrorListener(OnScriptErrorListener listener)
    {
        this.scriptErrorListener = listener;
        return this;
    }

    public AttrResourceInflaterListener getAttrResourceInflaterListener()
    {
        return attrResourcesInflaterListener;
    }

    @Override
    public PageRequest setAttrResourceInflaterListener(AttrResourceInflaterListener attrResourcesInflaterListener)
    {
        this.attrResourcesInflaterListener = attrResourcesInflaterListener;
        return this;
    }

    public RequestPropertyMap getRequestPropertyMap()
    {
        return requestPropertyMap;
    }

    @Override
    public PageRequest addRequestProperty(String name, String value)
    {
        requestPropertyMap.addProperty(name, value);
        return this;
    }

    @Override
    public PageRequest setRequestProperty(String name, String value)
    {
        requestPropertyMap.setProperty(name, value);
        return this;
    }

    @Override
    public boolean removeProperty(String name)
    {
        return requestPropertyMap.removeProperty(name);
    }

    @Override
    public String getRequestProperty(String name)
    {
        return requestPropertyMap.getPropertySingle(name);
    }

    @Override
    public Map<String, List<String>> getRequestProperties()
    {
        return requestPropertyMap.getPropertyUnmodifiableMap();
    }

    public HttpParams getHttpParams()
    {
        return httpParams;
    }

    @Override
    public PageRequest setConnectTimeout(int timeoutMillis)
    {
        this.connectTimeout = timeoutMillis;
        return this;
    }

    @Override
    public int getConnectTimeout()
    {
        return connectTimeout;
    }

    public PageRequest setReadTimeout(int timeoutMillis)
    {
        this.readTimeout = timeoutMillis;
        return this;
    }

    public int getReadTimeout()
    {
        return readTimeout;
    }

    public boolean isSynchronous()
    {
        return sync;
    }

    @Override
    public PageRequest setSynchronous(boolean sync)
    {
        this.sync = sync;
        return this;
    }

    public String getURL()
    {
        return url;
    }

    @Override
    public PageRequest setURL(String url)
    {
        this.url = url;
        this.pageURLBase = HttpUtil.getBasePathOfURL(url);
        return this;
    }

    public String getPageURLBase()
    {
        return pageURLBase;
    }

    @Override
    public void execute()
    {
        // El PageRequestImpl debe poder ser reutilizado si quiere el usuario
        if (url == null) throw new ItsNatDroidException("Missing URL");

        Resources res = getContext().getResources();

        String pageURLBase = getPageURLBase();
        HttpRequestData httpRequestData = new HttpRequestData(this);
        Map<String,ParsedResource> urlResDownloadedMap = new HashMap<String,ParsedResource>();
        XMLDOMRegistry xmlDOMRegistry = browser.getItsNatDroidImpl().getXMLDOMRegistry();

        XMLDOMParserContext xmlDOMParserContext = new XMLDOMParserContext(xmlDOMRegistry,res);

        if (sync)
            executeSync(url,pageURLBase,httpRequestData,urlResDownloadedMap,xmlDOMParserContext);
        else
            executeAsync(url,pageURLBase,httpRequestData,urlResDownloadedMap,xmlDOMParserContext);
    }

    private void executeSync(String url,String pageURLBase,HttpRequestData httpRequestData,Map<String,ParsedResource> urlResDownloadedMap,XMLDOMParserContext xmlDOMParserContext)
    {
        PageRequestResult pageRequestResult;
        try
        {
            pageRequestResult = executeInBackground(url,pageURLBase, httpRequestData,urlResDownloadedMap,xmlDOMParserContext);
        }
        catch(Exception ex)
        {
            // Aunque la excepción pueda ser capturada por el usuario al llamar al método público síncrono, tenemos
            // que respetar su decisión de usar un listener
            onFinishError(this,ex);

            return; // No se puede continuar
        }

        onFinishOk(this, pageRequestResult);
    }

    private void executeAsync(String url,String pageURLBase,HttpRequestData httpRequestData,Map<String,ParsedResource> urlResDownloadedMap,XMLDOMParserContext xmlDOMParserContext)
    {
        HttpGetPageAsyncTask task = new HttpGetPageAsyncTask(this,url,pageURLBase,httpRequestData,urlResDownloadedMap,xmlDOMParserContext);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); // Con execute() a secas se ejecuta en un "pool" de un sólo hilo sin verdadero paralelismo
    }

    public static PageRequestResult executeInBackground(String url,String pageURLBase,HttpRequestData httpRequestData,
                                    Map<String,ParsedResource> urlResDownloadedMap,XMLDOMParserContext xmlDOMParserContext) throws Exception
    {
        // Ejecutado en multihilo en el caso async
        HttpRequestResultOKImpl result = HttpUtil.httpGet(url, httpRequestData,null, null);
        PageRequestResult pageReqResult = processHttpRequestResultMultiThread(result, pageURLBase, httpRequestData,urlResDownloadedMap,xmlDOMParserContext);
        return pageReqResult;
    }

    public static void onFinishOk(PageRequestImpl pageRequest,PageRequestResult pageRequestResult)
    {
        try
        {
            pageRequest.processResponse(pageRequestResult);
        }
        catch(Exception ex)
        {
            OnPageLoadErrorListener errorListener = pageRequest.getOnPageLoadErrorListener();
            if (errorListener != null)
            {
                HttpRequestResult resultError = (ex instanceof ItsNatDroidServerResponseException) ? ((ItsNatDroidServerResponseException)ex).getHttpRequestResult() : pageRequestResult.getHttpRequestResultOKImpl();
                errorListener.onError(pageRequest, ex, resultError); // Para poder recogerla desde fuera
            }
            else
            {
                if (ex instanceof ItsNatDroidException) throw (ItsNatDroidException)ex;
                else throw new ItsNatDroidException(ex);
            }
        }
    }

    public static void onFinishError(PageRequestImpl pageRequest,Exception ex)
    {
        HttpRequestResult result = (ex instanceof ItsNatDroidServerResponseException) ? ((ItsNatDroidServerResponseException)ex).getHttpRequestResult() : null;

        ItsNatDroidException exFinal = (ex instanceof ItsNatDroidException) ? (ItsNatDroidException)ex : new ItsNatDroidException(ex);

        OnPageLoadErrorListener errorListener = pageRequest.getOnPageLoadErrorListener();
        if (errorListener != null)
        {
            errorListener.onError(pageRequest, exFinal, result);
        }
        else
        {
            // No se ha cargado la página en este contexto, no tenemos por ejemplo un errorMode
            throw exFinal;
        }
    }


    private static PageRequestResult processHttpRequestResultMultiThread(HttpRequestResultOKImpl httpRequestResult,
                                        String pageURLBase, HttpRequestData httpRequestData,
                                        Map<String,ParsedResource> urlResDownloadedMap,XMLDOMParserContext xmlDOMParserContext) throws Exception
    {
        // Método ejecutado en hilo downloader NO UI

        String markup = httpRequestResult.getResponseText();
        String itsNatServerVersion = httpRequestResult.getItsNatServerVersion(); // Puede ser null (page no servida por ItsNat)
        XMLDOMRegistry xmlDOMRegistry = xmlDOMParserContext.getXMLDOMRegistry();

        ParsedResourceXMLDOM<XMLDOMLayout> resourceXMLDOM = xmlDOMRegistry.buildXMLDOMLayoutAndCachingByMarkupAndResDesc(markup,null, itsNatServerVersion, XMLDOMLayoutParser.LayoutType.PAGE, xmlDOMParserContext);
        XMLDOMLayoutPage xmlDOMLayoutPage = (XMLDOMLayoutPage)resourceXMLDOM.getXMLDOM();

        PageRequestResult pageReqResult = new PageRequestResult(httpRequestResult, xmlDOMLayoutPage,xmlDOMParserContext);

        {
            XMLDOMLayoutPageDownloader downloader = (XMLDOMLayoutPageDownloader) XMLDOMDownloader.createXMLDOMDownloader(xmlDOMLayoutPage,pageURLBase, httpRequestData,itsNatServerVersion,urlResDownloadedMap,xmlDOMParserContext);
            downloader.downloadRemoteResources();
        }

        if (xmlDOMLayoutPage instanceof XMLDOMLayoutPageItsNat)
        {
            XMLDOMLayoutPageItsNat xmldomLayoutPageParent = (XMLDOMLayoutPageItsNat)xmlDOMLayoutPage;
            String loadInitScript = xmldomLayoutPageParent.getLoadInitScript();
            if (loadInitScript != null) // Es nulo si el scripting está desactivado
            {
                XMLDOMLayoutPageItsNatDownloader downloader = XMLDOMLayoutPageItsNatDownloader.createXMLDOMLayoutPageItsNatDownloader(xmldomLayoutPageParent,pageURLBase, httpRequestData,itsNatServerVersion,urlResDownloadedMap,xmlDOMParserContext);
                LinkedList<DOMAttrRemote> attrRemoteListBSParsed = downloader.parseBeanShellAndDownloadRemoteResources(loadInitScript);

                if (attrRemoteListBSParsed != null)
                    pageReqResult.setAttrRemoteListBSParsed(attrRemoteListBSParsed);
            }
        }

        return pageReqResult;
    }



    private void processResponse(PageRequestResult pageRequestResult)
    {
        HttpRequestResultOKImpl httpReqResult = pageRequestResult.getHttpRequestResultOKImpl();

        if (!MimeUtil.MIME_ANDROID_LAYOUT.equals(httpReqResult.getMimeType()))
            throw new ItsNatDroidServerResponseException("Expected " + MimeUtil.MIME_ANDROID_LAYOUT + " MIME in Content-Type:" + httpReqResult.getMimeType(),httpReqResult);

        String itsNatServerVersion = httpReqResult.getItsNatServerVersion();

        PageImpl page = itsNatServerVersion != null ? new PageItsNatImpl(this,pageRequestResult,itsNatServerVersion) : new PageNotItsNatImpl(this,pageRequestResult);
        OnPageLoadListener pageListener = getOnPageLoadListener();
        if (pageListener != null) pageListener.onPageLoad(page);
    }

    public PageRequestImpl clone()
    {
        return new PageRequestImpl(this);
    }

}
