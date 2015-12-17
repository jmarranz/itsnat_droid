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
    public Map<String,String> httpHeaders;
    public boolean sslSelfSignedAllowed;

    public HttpRequestData(PageRequestImpl pageRequest)
    {
        ItsNatDroidBrowserImpl browser = pageRequest.getItsNatDroidBrowserImpl();

        this.httpFileCache = browser.getHttpFileCache();
        this.httpContext = browser.getHttpContext();
        this.requestPropertyMap = pageRequest.getRequestPropertyMap().copy(); // Así permitimos en el PageRequest modificarlo y volver a llamar a execute(). En las requests sincronas no haria falta clonar pero no es importante
        this.connectTimeout = pageRequest.getConnectTimeout();
        this.readTimeout = pageRequest.getReadTimeout();
        this.httpParams = pageRequest.getHttpParams().copy();
        this.httpHeaders = pageRequest.createHttpHeaders(); // No hace falta clone porque createHttpHeaders() crea un nuevo Map
        this.sslSelfSignedAllowed = browser.isSSLSelfSignedAllowed();
    }

    public void setReadTimeout(long timeout)
    {
        // ESTO HA DE CAMBIARSE PARA SER COMPATIBLE CON HttpUrlConnection

        int soTimeout = timeout < 0 ? Integer.MAX_VALUE : (int) timeout;
        httpParams.setIntParameter("http.socket.timeout", soTimeout);

        this.readTimeout = (int)timeout;
    }

}
