package org.itsnat.droid.impl.browser;

import android.os.AsyncTask;

import org.apache.http.params.HttpParams;
import org.itsnat.droid.ClientErrorMode;
import org.itsnat.droid.GenericHttpClient;
import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidServerResponseException;
import org.itsnat.droid.OnHttpRequestErrorListener;
import org.itsnat.droid.OnHttpRequestListener;
import org.itsnat.droid.impl.util.NameValue;
import org.itsnat.droid.impl.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 9/10/14.
 */
public class GenericHttpClientImpl extends GenericHttpClientBaseImpl implements GenericHttpClient
{
    protected String userUrl;
    protected String method = "POST"; // Por defecto
    protected RequestPropertyMap requestPropertyMap;
    protected HttpParams httpParams;
    protected int connectTimeout;
    protected int readTimeout;
    protected List<NameValue> paramList = new ArrayList<NameValue>(10);
    protected String overrideMime;
    protected int errorMode = ClientErrorMode.SHOW_SERVER_AND_CLIENT_ERRORS;

    public GenericHttpClientImpl(ItsNatDocImpl itsNatDoc)
    {
        super(itsNatDoc);
        PageImpl page = itsNatDoc.getPageImpl();
        this.requestPropertyMap = page.getRequestPropertyMapImpl().copy();
        this.httpParams = page.getHttpParams().copy();
        this.connectTimeout = page.getConnectTimeout();
        this.readTimeout = page.getReadTimeout();
    }

    @Override
    public GenericHttpClient setClientErrorMode(int errorMode)
    {
        setClientErrorModeNotFluid(errorMode);
        return this;
    }

    public int getClientErrorMode()
    {
        return errorMode;
    }

    public void setClientErrorModeNotFluid(int errorMode)
    {
        // if (errorMode == ClientErrorMode.NOT_CATCH_ERRORS) throw new ItsNatDroidException("ClientErrorMode.NOT_CATCH_ERRORS is not supported"); // No tiene mucho sentido porque el objetivo es dejar fallar y si el usuario no ha registrado "error listeners" ItsNat Droid deja siempre fallar lanzando la excepción
        this.errorMode = errorMode;
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
    public GenericHttpClient setRequestMethod(String method)
    {
        setMethodNotFluid(method);
        return this;
    }

    public void setMethodNotFluid(String method)
    {
        this.method = method;
    }

    @Override
    public GenericHttpClient setURL(String url)
    {
        setURLNotFluid(url);
        return this;
    }

    private void setURLNotFluid(String url)
    {
        this.userUrl = url;
    }


    protected String getFinalURL()
    {
        // En el ejemplo ItsNatDroidServletNoItsNat se hace un ejemplo de llamada a GenericHttpClient con código custom en donde no se especifica un userUrl por lo que se usa el pageURLBase de contexto
        // que en ese caso es el path al servlet ItsNatDroidServletNoItsNat, de esa manera nos evitamos formar un URL y las llamadas específicas las hacemos a través de los parámetros
        // Podría ser también un path a un JSP (a fin de cuentas un JSP es un servlet).

        return StringUtil.isEmpty(userUrl) ? itsNatDoc.getPageImpl().getPageURLBase() : userUrl; // Como se puede ver seguridad de "single server" ninguna
    }

    public void addParamNotFluid(String name,Object value)
    {
        // Si se añade el mismo parámetro varias veces, es el caso de multivalor
        paramList.add(new NameValue(name, value));
    }

    public void clearParamsNotFluid()
    {
        paramList.clear();
    }

    @Override
    public GenericHttpClient addParameter(String name, Object value)
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

    public void request(boolean async) // Interno
    {
        if (async)
            requestAsync();
        else
            requestSync();
    }

    @Override
    public HttpRequestResult requestSync()
    {
        String url = getFinalURL();
        HttpRequestData httpRequestData = new HttpRequestData(this);
        List<NameValue> paramList = this.paramList; // No hace falta clonar pues es síncrono

        HttpRequestResultOKImpl result;
        try
        {
            result = executeInBackground(this,method, url, httpRequestData, paramList, overrideMime);
        }
        catch (Exception ex)
        {
            int errorMode = getClientErrorMode();
            onFinishError(this,ex, httpRequestListener,errorListener,errorMode);

            return null; // No podemos seguir
        }

        onFinishOk(this, result, httpRequestListener, errorListener);

        return result;
    }


    @Override
    public void requestAsync()
    {
        String url = getFinalURL();
        HttpRequestData httpRequestData = new HttpRequestData(this);
        List<NameValue> paramList = new ArrayList<NameValue>(this.paramList); // hace una copia, los NameValue son de sólo lectura por lo que no hay problema de compartirlos en hilos
        GenericHttpClientAsyncTask task = new GenericHttpClientAsyncTask(this,method,url,httpRequestData, paramList, httpRequestListener,errorListener,overrideMime);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); // Con execute() a secas se ejecuta en un "pool" de un sólo hilo sin verdadero paralelismo
    }

    protected static HttpRequestResultOKImpl executeInBackground(GenericHttpClientImpl parent,String method,String url,HttpRequestData httpRequestData,List<NameValue> paramList,String overrideMime) throws Exception
    {
        // Multihilo en el caso async
        return HttpUtil.httpAction(method,url, httpRequestData, paramList, overrideMime);
    }

    public static void onFinishOk(GenericHttpClientImpl parent,HttpRequestResultOKImpl result,OnHttpRequestListener listener,OnHttpRequestErrorListener errorListener)
    {
        try
        {
            parent.processResult(result,listener);
        }
        catch(Exception ex)
        {
            if (errorListener != null)
            {
                HttpRequestResult resultError = (ex instanceof ItsNatDroidServerResponseException) ? ((ItsNatDroidServerResponseException)ex).getHttpRequestResult() : result;
                errorListener.onError(parent.getPageImpl(), ex, resultError);
            }
            else
            {
                if (ex instanceof ItsNatDroidException) throw (ItsNatDroidException)ex;
                else throw new ItsNatDroidException(ex);
            }
        }
    }


    public static void onFinishError(GenericHttpClientImpl parent,Exception ex,OnHttpRequestListener listener,OnHttpRequestErrorListener errorListener,int errorMode)
    {
        HttpRequestResult result = (ex instanceof ItsNatDroidServerResponseException) ?  ((ItsNatDroidServerResponseException)ex).getHttpRequestResult() : null;

        ItsNatDroidException exFinal = GenericHttpClientBaseImpl.convertException(ex);

        if (errorListener != null)
        {
            errorListener.onError(parent.getPageImpl(),exFinal, result);
        }
        else
        {
            if (errorMode != ClientErrorMode.NOT_CATCH_ERRORS)
            {
                // Error del servidor, lo normal es que haya lanzado una excepción
                ItsNatDocImpl itsNatDoc = parent.getItsNatDocImpl();
                itsNatDoc.showErrorMessage(true, result,exFinal, errorMode);
            }
            else throw exFinal;
        }
    }
}
