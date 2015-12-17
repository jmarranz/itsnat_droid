package org.itsnat.droid.impl.browser;

import android.os.AsyncTask;

import org.apache.http.params.HttpParams;
import org.itsnat.droid.GenericHttpClient;
import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.OnHttpRequestErrorListener;
import org.itsnat.droid.OnHttpRequestListener;
import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocImpl;
import org.itsnat.droid.impl.httputil.RequestPropertyMap;
import org.itsnat.droid.impl.util.NameValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 9/10/14.
 */
public class GenericHttpClientImpl extends GenericHttpClientBaseImpl implements GenericHttpClient
{
    protected RequestPropertyMap requestPropertyMap;
    protected int connectTimeout;
    protected int readTimeout;
    protected HttpParams httpParams;
    protected List<NameValue> paramList = new ArrayList<NameValue>(10);
    protected String overrideMime;

    public GenericHttpClientImpl(ItsNatDocImpl itsNatDoc)
    {
        super(itsNatDoc);
        PageImpl page = itsNatDoc.getPageImpl();
        this.requestPropertyMap = page.getRequestPropertyMapImpl().copy();
        this.connectTimeout = page.getConnectTimeout();
        this.readTimeout = page.getReadTimeout();
        this.httpParams = page.getHttpParams().copy();
    }

    @Override
    public GenericHttpClient setErrorMode(int errorMode)
    {
        setErrorModeNotFluid(errorMode);
        return this;
    }

    @Override
    public GenericHttpClient setOnHttpRequestListener(OnHttpRequestListener listener)
    {
        setOnHttpRequestListenerNotFluid(listener);
        return this;
    }

    @Override
    public GenericHttpClient setOnHttpRequestErrorListener(OnHttpRequestErrorListener httpErrorListener)
    {
        setOnHttpRequestErrorListenerNotFluid(httpErrorListener);
        return this;
    }

    @Override
    public GenericHttpClient setMethod(String method)
    {
        setMethodNotFluid(method);
        return this;
    }

    @Override
    public GenericHttpClient setURL(String url)
    {
        setURLNotFluid(url);
        return this;
    }

    public void addParamNotFluid(String name,String value)
    {
        // Si se añade el mismo parámetro varias veces, es el caso de multivalor
        paramList.add(new NameValue(name, value));
    }

    public void clearParamsNotFluid()
    {
        paramList.clear();
    }

    @Override
    public GenericHttpClient addParameter(String name, String value)
    {
        addParamNotFluid(name, value);
        return this;
    }

    @Override
    public GenericHttpClient clearParameters()
    {
        clearParamsNotFluid();
        return this;
    }

    public RequestPropertyMap getRequestPropertyMap()
    {
        return requestPropertyMap;
    }

    @Override
    public GenericHttpClient addRequestProperty(String name, String value)
    {
        requestPropertyMap.addProperty(name, value);
        return this;
    }

    @Override
    public GenericHttpClient setRequestProperty(String name, String value)
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
    public GenericHttpClient setConnectTimeout(int timeoutMillis)
    {
        this.connectTimeout = timeoutMillis;

        int timeout = timeoutMillis < 0 ? Integer.MAX_VALUE : (int) timeoutMillis;
        httpParams.setIntParameter("http.connection.timeout", timeout);
        return this;
    }

    @Override
    public int getConnectTimeout()
    {
        return connectTimeout;
    }

    @Override
    public GenericHttpClient setReadTimeout(int timeoutMillis)
    {
        this.readTimeout = timeoutMillis;

        // PROVISIONAL
        int soTimeout = timeoutMillis < 0 ? Integer.MAX_VALUE : (int) timeoutMillis;
        httpParams.setIntParameter("http.socket.timeout", soTimeout);
        return this;
    }

    @Override
    public int getReadTimeout()
    {
        return readTimeout;
    }


    public void setOverrideMimeTypeNotFluid(String mime)
    {
        this.overrideMime = mime;
    }

    @Override
    public GenericHttpClient setOverrideMimeType(String mime)
    {
        setOverrideMimeTypeNotFluid(mime);
        return this;
    }

    public HttpRequestResult request(boolean async) // No es público
    {
        if (async)
        {
            requestAsync();
            return null;
        }
        else return requestSync();
    }

    @Override
    public HttpRequestResult requestSync()
    {
        PageImpl page = getPageImpl();
        String url = getFinalURL();

        HttpRequestData httpRequestData = new HttpRequestData(this);
        List<NameValue> params = this.paramList;

        HttpRequestResultOKImpl result;
        try
        {
            result = HttpUtil.httpPost(url, httpRequestData, params,overrideMime);
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            ItsNatDroidException exFinal = convertException(ex);
            throw exFinal;

            // No usamos aquí el OnEventErrorListener porque la excepción es capturada por un catch anterior que sí lo hace
        }

        processResult(result,listener,errorMode);

        return result;
    }

    @Override
    public void requestAsync()
    {
        String url = getFinalURL();
        HttpActionGenericAsyncTask postTask = new HttpActionGenericAsyncTask(this,method,url, paramList, listener,errorListener,errorMode,overrideMime);
        postTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); // Con execute() a secas se ejecuta en un "pool" de un sólo hilo sin verdadero paralelismo
    }

}
