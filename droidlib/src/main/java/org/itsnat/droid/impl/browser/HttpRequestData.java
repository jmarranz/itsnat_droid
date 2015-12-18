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
        // Para acciones posteriores a la carga de la página tal y como carga de recursos, envío de eventos etc una vez cargada la página reutilizando su configuración de red,
        // no es necesario clonar salvo que encontremos algún caso que merezca la pena un tunning para ciertos recursos (por ej el read timeout)
        this(page.getPageRequestClonedImpl(),false);
    }

    public HttpRequestData(PageRequestImpl pageRequest)
    {
        // Carga de página, clonamos y así permitimos en el PageRequest modificarlo y volver a llamar a execute(). En las requests síncronas no haria falta clonar pero no es importante
        this(pageRequest,true);
    }

    private HttpRequestData(PageRequestImpl pageRequest,boolean copy)
    {
        ItsNatDroidBrowserImpl browser = pageRequest.getItsNatDroidBrowserImpl();

        RequestPropertyMap requestPropertyMap = pageRequest.getRequestPropertyMap();
        HttpParams httpParams = pageRequest.getHttpParams();

        this.httpFileCache = browser.getHttpFileCache();
        this.httpContext = browser.getHttpContext();
        this.requestPropertyMap = copy ? requestPropertyMap.copy() : requestPropertyMap;
        this.connectTimeout = pageRequest.getConnectTimeout();
        this.readTimeout = pageRequest.getReadTimeout();
        this.httpParams = copy ? httpParams.copy() : httpParams;
        this.sslSelfSignedAllowed = browser.isSSLSelfSignedAllowed();

        pageRequest.addCurrentDeviceStateHttpHeaders(requestPropertyMap); // Da igual el flag copy, hay que enviar el estado del dispositivo lo más fresco posible
    }

    public HttpRequestData(GenericHttpClientImpl genericHttpClient)
    {
        PageImpl page = genericHttpClient.getItsNatDocImpl().getPageImpl();
        ItsNatDroidBrowserImpl browser = page.getItsNatDroidBrowserImpl();

        this.httpFileCache = browser.getHttpFileCache();
        this.httpContext = browser.getHttpContext();
        this.requestPropertyMap = genericHttpClient.getRequestPropertyMap().copy();
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

}
