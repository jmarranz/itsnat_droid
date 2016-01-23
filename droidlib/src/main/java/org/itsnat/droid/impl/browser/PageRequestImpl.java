package org.itsnat.droid.impl.browser;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.DisplayMetrics;

import org.apache.http.params.HttpParams;
import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.ClientErrorMode;
import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidServerResponseException;
import org.itsnat.droid.OnPageLoadErrorListener;
import org.itsnat.droid.OnPageLoadListener;
import org.itsnat.droid.OnScriptErrorListener;
import org.itsnat.droid.PageRequest;
import org.itsnat.droid.impl.browser.serveritsnat.PageItsNatImpl;
import org.itsnat.droid.impl.browser.servernotitsnat.PageNotItsNatImpl;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.dom.layout.DOMScript;
import org.itsnat.droid.impl.dom.layout.DOMScriptRemote;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPage;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutPageItsNat;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.domparser.layout.XMLDOMLayoutParser;
import org.itsnat.droid.impl.httputil.RequestPropertyMap;
import org.itsnat.droid.impl.util.MimeUtil;

import java.util.ArrayList;
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
    protected AttrLayoutInflaterListener attrLayoutInflaterListener;
    protected AttrDrawableInflaterListener attrDrawableInflaterListener;
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
        this.attrLayoutInflaterListener = origin.attrLayoutInflaterListener;
        this.attrDrawableInflaterListener = origin.attrDrawableInflaterListener;
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

    public AttrLayoutInflaterListener getAttrLayoutInflaterListener()
    {
        return attrLayoutInflaterListener;
    }

    @Override
    public PageRequest setAttrLayoutInflaterListener(AttrLayoutInflaterListener attrLayoutInflaterListener)
    {
        this.attrLayoutInflaterListener = attrLayoutInflaterListener;
        return this;
    }

    public AttrDrawableInflaterListener getAttrDrawableInflaterListener()
    {
        return attrDrawableInflaterListener;
    }

    @Override
    public PageRequest setAttrDrawableInflaterListener(AttrDrawableInflaterListener attrDrawableInflaterListener)
    {
        this.attrDrawableInflaterListener = attrDrawableInflaterListener;
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
        if (sync) executeSync(url);
        else executeAsync(url);
    }

    private void executeSync(String url)
    {
        // No hace falta clonar porque es síncrono el método

        HttpRequestData httpRequestData = new HttpRequestData(this);

        XMLDOMRegistry xmlDOMRegistry = browser.getItsNatDroidImpl().getXMLDOMRegistry();

        AssetManager assetManager = getContext().getResources().getAssets();

        String pageURLBase = getPageURLBase();

        PageRequestResult pageRequestResult = null;
        try
        {
            pageRequestResult = executeInBackground(url,pageURLBase, httpRequestData, xmlDOMRegistry, assetManager);
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

    private void executeAsync(String url)
    {
        HttpGetPageAsyncTask task = new HttpGetPageAsyncTask(this,url);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); // Con execute() a secas se ejecuta en un "pool" de un sólo hilo sin verdadero paralelismo
    }

    public static PageRequestResult executeInBackground(String url,String pageURLBase,HttpRequestData httpRequestData,XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager) throws Exception
    {
        // Ejecutado en multihilo en el caso async
        HttpRequestResultOKImpl result = HttpUtil.httpGet(url, httpRequestData,null, null);
        PageRequestResult pageReqResult = processHttpRequestResultMultiThread(result, pageURLBase, httpRequestData, xmlDOMRegistry, assetManager);
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
                                        XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager) throws Exception
    {
        // Método ejecutado en hilo downloader NO UI

        String markup = httpRequestResult.getResponseText();
        String itsNatServerVersion = httpRequestResult.getItsNatServerVersion(); // Puede ser null (no servida por ItsNat
        XMLDOMLayoutPage domLayout = (XMLDOMLayoutPage)xmlDOMRegistry.getXMLDOMLayoutCache(markup, itsNatServerVersion, XMLDOMLayoutParser.LayoutType.PAGE, assetManager);

        PageRequestResult pageReqResult = new PageRequestResult(httpRequestResult, domLayout);

        downloadXMLDOMRemoteResources(domLayout, pageURLBase, httpRequestData, xmlDOMRegistry, assetManager);


        if (domLayout instanceof XMLDOMLayoutPageItsNat)
        {
            XMLDOMLayoutPageItsNat xmldomLayoutPageParent = (XMLDOMLayoutPageItsNat)domLayout;
            String loadInitScript = xmldomLayoutPageParent.getLoadInitScript();
            if (loadInitScript != null) // Es nulo si el scripting está desactivado
            {
                LinkedList<DOMAttrRemote> attrRemoteListBSParsed = parseBeanShellAndDownloadRemoteResources(loadInitScript,itsNatServerVersion,xmldomLayoutPageParent,pageURLBase,
                        httpRequestData, xmlDOMRegistry, assetManager);

                if (attrRemoteListBSParsed != null)
                    pageReqResult.setAttrRemoteListBSParsed(attrRemoteListBSParsed);
            }
        }

        return pageReqResult;
    }

    private static void downloadResources(LinkedList<DOMAttrRemote> attrRemoteList,String pageURLBase,HttpRequestData httpRequestData,XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager) throws Exception
    {
        // llena los elementos de DOMAttrRemote attrRemoteList con el recurso descargado que le corresponde

        HttpResourceDownloader resDownloader = new HttpResourceDownloader(pageURLBase, httpRequestData, xmlDOMRegistry, assetManager);
        resDownloader.downloadResources(attrRemoteList);
    }

    private static String downloadScriptSync(String src, String pageURLBase, HttpRequestData httpRequestData)
    {
        src = HttpUtil.composeAbsoluteURL(src,pageURLBase);
        HttpRequestResultOKImpl result = HttpUtil.httpGet(src, httpRequestData, null,MimeUtil.MIME_BEANSHELL);
        return result.getResponseText();
    }

    public static void downloadXMLDOMRemoteResources(XMLDOM xmlDOM, String pageURLBase, HttpRequestData httpRequestData, XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager) throws Exception
    {
        {
            LinkedList<DOMAttrRemote> attrRemoteList = xmlDOM.getDOMAttrRemoteList();
            if (attrRemoteList != null)
            {
                downloadResources(attrRemoteList, pageURLBase, httpRequestData, xmlDOMRegistry, assetManager);
            }
        }

        if (xmlDOM instanceof XMLDOMLayoutPage)
        {
            // Tenemos que descargar los <script src="..."> remótamente de forma síncrona (podemos pues estamos en un hilo no UI downloader)
            ArrayList<DOMScript> scriptList = ((XMLDOMLayoutPage)xmlDOM).getDOMScriptList();
            if (scriptList != null)
            {
                for (int i = 0; i < scriptList.size(); i++)
                {
                    DOMScript script = scriptList.get(i);
                    if (script instanceof DOMScriptRemote)
                    {
                        DOMScriptRemote scriptRemote = (DOMScriptRemote) script;
                        String code = downloadScriptSync(scriptRemote.getSrc(), pageURLBase, httpRequestData);
                        scriptRemote.setCode(code);
                    }
                }
            }
        }
    }


    public static LinkedList<DOMAttrRemote> parseBeanShellAndDownloadRemoteResources(String code,String itsNatServerVersion,XMLDOMLayoutPageItsNat domLayout,String pageURLBase, HttpRequestData httpRequestData,
                                                                XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager) throws Exception
    {
        @SuppressWarnings("unchecked") LinkedList<DOMAttrRemote>[] attrRemoteListBSParsed = new LinkedList[1];
        @SuppressWarnings("unchecked") LinkedList<String>[] classNameListBSParsed = new LinkedList[1];
        @SuppressWarnings("unchecked") LinkedList<String>[] xmlMarkupListBSParsed = new LinkedList[1];

        domLayout.parseBSRemoteAttribs(code, attrRemoteListBSParsed, classNameListBSParsed, xmlMarkupListBSParsed);

        if (attrRemoteListBSParsed[0] != null)
        {
            // llena los elementos de DOMAttrRemote attrRemoteList con el recurso descargado que le corresponde
            downloadResources(attrRemoteListBSParsed[0], pageURLBase, httpRequestData, xmlDOMRegistry, assetManager);
        }

        if (classNameListBSParsed[0] != null)
        {
            XMLDOMLayoutPage[] xmldomLayoutPageArr = FragmentLayoutInserter.wrapAndParseMarkup(classNameListBSParsed[0], xmlMarkupListBSParsed[0], itsNatServerVersion, domLayout, xmlDOMRegistry, assetManager);
            for (XMLDOMLayoutPage xmlDOM : xmldomLayoutPageArr)
            {
                downloadXMLDOMRemoteResources(xmlDOM,pageURLBase,httpRequestData,xmlDOMRegistry,assetManager);
            }
        }

        return attrRemoteListBSParsed[0]; // Puede ser null
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
