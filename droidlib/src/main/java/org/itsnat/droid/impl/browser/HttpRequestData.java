package org.itsnat.droid.impl.browser;

import org.apache.http.protocol.HttpContext;
import org.itsnat.droid.impl.httputil.HttpParamMapImpl;

import java.util.Map;

/**
 * Created by jmarranz on 12/11/14.
 */
public class HttpRequestData
{
    public HttpFileCache httpFileCache;
    public HttpContext httpContext;
    public HttpParamMapImpl httpParamMapRequest;
    public HttpParamMapImpl httpParamMapDefault;
    public Map<String,String> httpHeaders;
    public boolean sslSelfSignedAllowed;

    public HttpRequestData(PageRequestImpl pageRequest)
    {
        ItsNatDroidBrowserImpl browser = pageRequest.getItsNatDroidBrowserImpl();

        HttpFileCache httpFileCache = browser.getHttpFileCache();
        HttpContext httpContext = browser.getHttpContext();
        HttpParamMapImpl httpParamMapRequest = pageRequest.getHttpParamMapImpl();
        HttpParamMapImpl httpParamMapDefault = browser.getHttpParamMapImpl();
        Map<String, String> httpHeaders = pageRequest.createHttpHeaders();
        boolean sslSelfSignedAllowed = browser.isSSLSelfSignedAllowed();

        this.httpFileCache = httpFileCache;
        this.httpContext = httpContext;
        this.httpParamMapRequest = httpParamMapRequest != null ? httpParamMapRequest.copy() : null; // En las requests sincronas no haria falta clonar pero no es importante
        this.httpParamMapDefault = httpParamMapDefault != null ? httpParamMapDefault.copy() : null;
        this.httpHeaders = httpHeaders; // No hace falta clone porque createHttpHeaders() crea un Map
        this.sslSelfSignedAllowed = sslSelfSignedAllowed;
    }

    public void setTimeout(long timeout)
    {
        if (httpParamMapRequest == null) httpParamMapRequest = new HttpParamMapImpl();
        setTimeout(timeout, httpParamMapRequest);
    }

    public static void setTimeout(long timeout,HttpParamMapImpl httpParamsRequest)
    {
        int soTimeout = timeout < 0 ? Integer.MAX_VALUE : (int) timeout;
        httpParamsRequest.setIntParameter("http.socket.timeout", soTimeout);
    }
}
