package org.itsnat.droid.impl.browser;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.httputil.RequestPropertyMap;

/**
 * Created by jmarranz on 12/11/14.
 */
public class HttpRequestData
{
    private HttpFileCache httpFileCache;
    private HttpContext httpContext;
    private RequestPropertyMap requestPropertyMap;
    private int connectTimeout;
    private int readTimeout;
    private HttpParams httpParams;
    private boolean sslSelfSignedAllowed;


    public HttpRequestData(PageImpl page)
    {
        // Para acciones posteriores a la carga de la página tal y como carga de recursos, envío de eventos etc una vez cargada la página reutilizando su configuración de red,
        // pero aun así clonamos la configuración pues en requestPropertyMap tenemos que poner los headers ItsNat más recientes posibles
        this(page.getPageRequestClonedImpl());
    }

    public HttpRequestData(PageRequestImpl pageRequest)
    {
        // Dos casos:
        // 1) Carga de página, clonamos y así permitimos en el PageRequest modificarlo y volver a llamar a execute(). En las requests síncronas no haria falta clonar pero no es importante
        // 2) Acciones posteriores a la carga de la página tal y como carga de recursos utilizando el getPageRequestClonedImpl()

        ItsNatDroidBrowserImpl browser = pageRequest.getItsNatDroidBrowserImpl();
        Context ctx = pageRequest.getContext();

        RequestPropertyMap requestPropertyMap = pageRequest.getRequestPropertyMap();
        HttpParams httpParams = pageRequest.getHttpParams();

        this.httpFileCache = browser.getHttpFileCache();
        this.httpContext = browser.getHttpContext();
        this.requestPropertyMap = requestPropertyMap.copy();
        this.connectTimeout = pageRequest.getConnectTimeout();
        this.readTimeout = pageRequest.getReadTimeout();
        this.httpParams = httpParams.copy();
        this.sslSelfSignedAllowed = browser.isSSLSelfSignedAllowed();

        setCurrentDeviceStateHttpHeaders(this.requestPropertyMap,browser,ctx); // Da igual el flag copy, hay que enviar el estado del dispositivo lo más fresco posible
    }

    public HttpRequestData(GenericHttpClientImpl genericHttpClient)
    {
        PageImpl page = genericHttpClient.getItsNatDocImpl().getPageImpl();
        ItsNatDroidBrowserImpl browser = page.getItsNatDroidBrowserImpl();
        Context ctx = page.getContext();

        this.httpFileCache = browser.getHttpFileCache();
        this.httpContext = browser.getHttpContext();
        this.requestPropertyMap = genericHttpClient.getRequestPropertyMap().copy();
        this.connectTimeout = genericHttpClient.getConnectTimeout();
        this.readTimeout = genericHttpClient.getReadTimeout();
        this.httpParams = genericHttpClient.getHttpParams().copy();
        this.sslSelfSignedAllowed = browser.isSSLSelfSignedAllowed();

        setCurrentDeviceStateHttpHeaders(this.requestPropertyMap,browser, ctx);
    }

    private void setCurrentDeviceStateHttpHeaders(RequestPropertyMap requestPropertyMap,ItsNatDroidBrowserImpl browser,Context ctx)
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

        // Da igual llamar a addProperty o setProperty pues siempre se llama en un requestPropertyMap clonado pues necesitamos datos frescos y que no se pisen entre hilos
        requestPropertyMap.addProperty("ItsNat-model", "" + Build.MODEL);
        requestPropertyMap.addProperty("ItsNat-sdk-int", "" + Build.VERSION.SDK_INT);
        requestPropertyMap.addProperty("ItsNat-lib-version-name", "" + libVersionName);
        requestPropertyMap.addProperty("ItsNat-lib-version-code", "" + libVersionCode);
        requestPropertyMap.addProperty("ItsNat-app-version-name", "" + pInfo.versionName);
        requestPropertyMap.addProperty("ItsNat-app-version-code", "" + pInfo.versionCode);
        requestPropertyMap.addProperty("ItsNat-display-width", "" + dm.widthPixels);
        requestPropertyMap.addProperty("ItsNat-display-height", "" + dm.heightPixels);
        requestPropertyMap.addProperty("ItsNat-display-density", "" + dm.density);
    }


    public HttpFileCache getHttpFileCache()
    {
        return httpFileCache;
    }

    public HttpContext getHttpContext()
    {
        return httpContext;
    }

    public RequestPropertyMap getRequestPropertyMap()
    {
        return requestPropertyMap;
    }

    public int getConnectTimeout()
    {
        return connectTimeout;
    }

    public int getReadTimeout()
    {
        return readTimeout;
    }

    public void setReadTimeout(int timeout)
    {
        this.readTimeout = timeout;
    }

    public HttpParams getHttpParams()
    {
        return httpParams;
    }

    public boolean isSslSelfSignedAllowed()
    {
        return sslSelfSignedAllowed;
    }
}
