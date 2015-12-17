package org.itsnat.droid;

import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 10/10/14.
 */
public interface GenericHttpClient
{
    //public int getErrorMode();
    public GenericHttpClient setErrorMode(int errorMode);
    public GenericHttpClient setOnHttpRequestListener(OnHttpRequestListener listener);
    public GenericHttpClient setOnHttpRequestErrorListener(OnHttpRequestErrorListener listener);
    public GenericHttpClient setMethod(String method);
    public GenericHttpClient setURL(String url);
    public GenericHttpClient addParameter(String name, String value);
    public GenericHttpClient clearParameters();
    public GenericHttpClient addRequestProperty(String name, String value);
    public GenericHttpClient setRequestProperty(String name, String value);
    public boolean removeProperty(String name);
    public String getRequestProperty(String name);
    public Map<String, List<String>> getRequestProperties();
    public GenericHttpClient setConnectTimeout(int timeoutMillis);
    public int getConnectTimeout();
    public GenericHttpClient setReadTimeout(int timeoutMillis);
    public int getReadTimeout();
    public GenericHttpClient setOverrideMimeType(String mime);
    public HttpRequestResult request(boolean async);
    public HttpRequestResult requestSync();
    public void requestAsync();
}
