package org.itsnat.droid.impl.browser;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.util.DisplayMetrics;

import org.apache.http.params.HttpParams;
import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidServerResponseException;
import org.itsnat.droid.OnPageLoadErrorListener;
import org.itsnat.droid.OnPageLoadListener;
import org.itsnat.droid.PageRequest;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.layout.DOMScript;
import org.itsnat.droid.impl.dom.layout.DOMScriptRemote;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.domparser.XMLDOMRegistry;
import org.itsnat.droid.impl.httputil.RequestPropertyMap;
import org.itsnat.droid.impl.util.MimeUtil;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
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
    protected int connectTimeout;
    protected int readTimeout;
    protected HttpParams httpParams;
    protected int bitmapDensityReference = DisplayMetrics.DENSITY_XHIGH;
    protected OnPageLoadListener pageListener;
    protected OnPageLoadErrorListener errorListener;
    protected AttrLayoutInflaterListener attrLayoutInflaterListener;
    protected AttrDrawableInflaterListener attrDrawableInflaterListener;
    protected boolean sync = false;
    protected String url;
    protected String urlBase;


    public PageRequestImpl(ItsNatDroidBrowserImpl browser)
    {
        this.browser = browser;
        this.requestPropertyMap = browser.getRequestPropertyMap().copy();
        this.connectTimeout = browser.getConnectTimeout();
        this.readTimeout = browser.getReadTimeout();
        this.httpParams = browser.getHttpParams().copy();
    }

    public PageRequestImpl(PageRequestImpl origin) // clone to this
    {
        this.browser = origin.browser;
        this.ctx = origin.ctx;
        this.requestPropertyMap = origin.requestPropertyMap.copy();
        this.connectTimeout = origin.connectTimeout;
        this.readTimeout = origin.readTimeout;
        this.httpParams = origin.httpParams.copy();
        this.bitmapDensityReference = origin.bitmapDensityReference;
        this.pageListener = origin.pageListener;
        this.errorListener = origin.errorListener;
        this.attrLayoutInflaterListener = origin.attrLayoutInflaterListener;
        this.attrDrawableInflaterListener = origin.attrDrawableInflaterListener;
        this.sync = origin.sync;
        this.url = origin.url;
        this.urlBase = origin.urlBase;
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

    public OnPageLoadListener getOnPageLoadListener()
    {
        return pageListener;
    }

    @Override
    public PageRequest setOnPageLoadListener(OnPageLoadListener pageListener)
    {
        this.pageListener = pageListener;
        return this;
    }

    public OnPageLoadErrorListener getOnPageLoadErrorListener()
    {
        return errorListener;
    }

    @Override
    public PageRequest setOnPageLoadErrorListener(OnPageLoadErrorListener errorListener)
    {
        this.errorListener = errorListener;
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

    public HttpParams getHttpParams() { return httpParams; }

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

        // PROVISIONAL
        int soTimeout = timeoutMillis < 0 ? Integer.MAX_VALUE : (int) timeoutMillis;
        httpParams.setIntParameter("http.socket.timeout", soTimeout);

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
        this.urlBase = HttpUtil.getBasePathOfURL(url);
        return this;
    }

    public String getURLBase()
    {
        return urlBase;
    }



    public Map<String,String> createHttpHeaders()
    {
        // http://stackoverflow.com/questions/17481341/how-to-get-android-screen-size-programmatically-once-and-for-all
        // Recuerda que cambia con la orientación por eso hay que enviarlos "frescos"
        Resources resources = ctx.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        ItsNatDroidImpl itsNatDroid = browser.getItsNatDroidImpl();
        int libVersionCode = itsNatDroid.getVersionCode();
        String libVersionName = itsNatDroid.getVersionName();
        PackageInfo pInfo;
        try { pInfo = ctx.getPackageManager().getPackageInfo( ctx.getPackageName(), 0); }
        catch(PackageManager.NameNotFoundException ex) { throw new ItsNatDroidException(ex); }

        Map<String, String> httpHeaders = new HashMap<String, String>();

        httpHeaders.put("ItsNat-model", "" + Build.MODEL);
        httpHeaders.put("ItsNat-sdk-int", "" + Build.VERSION.SDK_INT);
        httpHeaders.put("ItsNat-lib-version-name", "" + libVersionName);
        httpHeaders.put("ItsNat-lib-version-code", "" + libVersionCode);
        httpHeaders.put("ItsNat-app-version-name", "" + pInfo.versionName);
        httpHeaders.put("ItsNat-app-version-code", "" + pInfo.versionCode);
        httpHeaders.put("ItsNat-display-width", "" + dm.widthPixels);
        httpHeaders.put("ItsNat-display-height", "" + dm.heightPixels);
        httpHeaders.put("ItsNat-display-density", "" + dm.density);

        return httpHeaders;
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

        String pageURLBase = getURLBase();

        PageRequestResult result = null;
        try
        {
            HttpRequestResultOKImpl httpReqResult = HttpUtil.httpGet(url,httpRequestData,null,null);

            AssetManager assetManager = getContext().getResources().getAssets();
            result = processHttpRequestResult(httpReqResult,pageURLBase, httpRequestData,xmlDOMRegistry,assetManager);
        }
        catch(Exception ex)
        {
            // Aunque la excepción pueda ser capturada por el usuario al llamar al método público síncrono, tenemos
            // que respetar su decisión de usar un listener
            OnPageLoadErrorListener errorListener = getOnPageLoadErrorListener();
            if (errorListener != null)
            {
                errorListener.onError(ex, this, result.getHttpRequestResultOKImpl()); // Para poder recogerla desde fuera
                return;
            }
            else
            {
                if (ex instanceof ItsNatDroidException) throw (ItsNatDroidException)ex;
                else throw new ItsNatDroidException(ex);
            }
        }

        processResponse(result);
    }

    private void executeAsync(String url)
    {
        HttpGetPageAsyncTask task = new HttpGetPageAsyncTask(this,url);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); // Con execute() a secas se ejecuta en un "pool" de un sólo hilo sin verdadero paralelismo
    }

    public static PageRequestResult processHttpRequestResult(HttpRequestResultOKImpl result,
                                        String pageURLBase,HttpRequestData httpRequestData,
                                        XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager) throws Exception
    {
        // Método ejecutado en hilo downloader NO UI

        String markup = result.getResponseText();
        String itsNatServerVersion = result.getItsNatServerVersion(); // Puede ser null (no servida por ItsNat
        XMLDOMLayout domLayout = xmlDOMRegistry.getXMLDOMLayoutCache(markup, itsNatServerVersion, true, assetManager);


        // Tenemos que descargar los <script src="..."> remótamente de forma síncrona (podemos pues estamos en un hilo UI downloader)
        ArrayList<DOMScript> scriptList = domLayout.getDOMScriptList();
        if (scriptList != null)
        {
            for (int i = 0; i < scriptList.size(); i++)
            {
                DOMScript script = scriptList.get(i);
                if (script instanceof DOMScriptRemote)
                {
                    DOMScriptRemote scriptRemote = (DOMScriptRemote) script;
                    String code = downloadScript(scriptRemote.getSrc(),pageURLBase, httpRequestData);
                    scriptRemote.setCode(code);
                }
            }
        }


        LinkedList<DOMAttrRemote> attrRemoteList = domLayout.getDOMAttrRemoteList();
        if (attrRemoteList != null)
        {
            HttpResourceDownloader resDownloader = new HttpResourceDownloader(pageURLBase,httpRequestData,xmlDOMRegistry,assetManager);
            resDownloader.downloadResources(attrRemoteList);
        }

        PageRequestResult pageReqResult = new PageRequestResult(result, domLayout);
        return pageReqResult;
    }

    public void processResponse(PageRequestResult result)
    {
        HttpRequestResultOKImpl httpReqResult = result.getHttpRequestResultOKImpl();

        if (!MimeUtil.MIME_ANDROID_LAYOUT.equals(httpReqResult.getMimeType()))
            throw new ItsNatDroidServerResponseException("Expected " + MimeUtil.MIME_ANDROID_LAYOUT + " MIME in Content-Type:" + httpReqResult.getMimeType(),httpReqResult);

        String itsNatServerVersion = httpReqResult.getItsNatServerVersion();

        PageImpl page = itsNatServerVersion != null ? new PageItsNatImpl(this,result,itsNatServerVersion) : new PageNotItsNatImpl(this,result);
        OnPageLoadListener pageListener = getOnPageLoadListener();
        if (pageListener != null) pageListener.onPageLoad(page);
    }

    public PageRequestImpl clone()
    {
        return new PageRequestImpl(this);
    }

    private static String downloadScript(String src,String pageURLBase,HttpRequestData httpRequestData) throws SocketTimeoutException
    {
        src = HttpUtil.composeAbsoluteURL(src,pageURLBase);
        HttpRequestResultOKImpl result = HttpUtil.httpGet(src, httpRequestData, null,MimeUtil.MIME_BEANSHELL);
        return result.getResponseText();
    }
}
