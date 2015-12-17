package org.itsnat.droid.impl.browser;

import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.itsnat.droid.impl.httputil.RequestPropertyMap;

import java.util.Map;

/**
 * Created by jmarranz on 12/11/14.
 */
public class HttpRequestData
{
    public HttpFileCache httpFileCache;
    public HttpContext httpContext;
    public RequestPropertyMap requestPropertyMap;
    public int connectTimeout;
    public int readTimeout;
    public HttpParams httpParams;
    public boolean sslSelfSignedAllowed;


    public HttpRequestData(PageImpl page)
    {
        this(page.getPageRequestClonedImpl()); // Para acciones de carga de recursos, eventos etc una vez cargada la página reutilizando su configuración de red
    }

    public HttpRequestData(PageRequestImpl pageRequest)
    {
        ItsNatDroidBrowserImpl browser = pageRequest.getItsNatDroidBrowserImpl();

        this.httpFileCache = browser.getHttpFileCache();
        this.httpContext = browser.getHttpContext();
        this.requestPropertyMap = pageRequest.getRequestPropertyMap().copy(); // Así permitimos en el PageRequest modificarlo y volver a llamar a execute(). En las requests sincronas no haria falta clonar pero no es importante
        this.connectTimeout = pageRequest.getConnectTimeout();
        this.readTimeout = pageRequest.getReadTimeout();
        this.httpParams = pageRequest.getHttpParams().copy();
        this.sslSelfSignedAllowed = browser.isSSLSelfSignedAllowed();

        pageRequest.addCurrentDeviceStateHttpHeaders(requestPropertyMap);
    }

    public HttpRequestData(GenericHttpClientImpl genericHttpClient)
    {
        PageImpl page = genericHttpClient.getItsNatDocImpl().getPageImpl();
        ItsNatDroidBrowserImpl browser = page.getItsNatDroidBrowserImpl();

        this.httpFileCache = browser.getHttpFileCache();
        this.httpContext = browser.getHttpContext();
        this.requestPropertyMap = genericHttpClient.getRequestPropertyMap().copy(); // Así permitimos en el PageRequest modificarlo y volver a llamar a execute(). En las requests sincronas no haria falta clonar pero no es importante
        this.connectTimeout = genericHttpClient.getConnectTimeout();
        this.readTimeout = genericHttpClient.getReadTimeout();
        this.httpParams = genericHttpClient.getHttpParams().copy();
        this.sslSelfSignedAllowed = browser.isSSLSelfSignedAllowed();

        page.getPageRequestClonedImpl().addCurrentDeviceStateHttpHeaders(requestPropertyMap);
    }

    public void setReadTimeout(long timeout)
    {
        // ESTO HA DE CAMBIARSE PARA SER COMPATIBLE CON HttpUrlConnection

        int soTimeout = timeout < 0 ? Integer.MAX_VALUE : (int) timeout;
        httpParams.setIntParameter("http.socket.timeout", soTimeout);

        this.readTimeout = (int)timeout;
    }

    private void copyDeviceNowHttpHeaders()
    {

    }
}
