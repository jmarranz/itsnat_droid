package org.itsnat.droid.impl.browser;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.itsnat.droid.ItsNatDroid;
import org.itsnat.droid.ItsNatDroidBrowser;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.PageRequest;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.serveritsnat.CustomFunction;
import org.itsnat.droid.impl.browser.serveritsnat.ItsNatSessionImpl;
import org.itsnat.droid.impl.httputil.RequestPropertyMap;
import org.itsnat.droid.impl.util.MapLight;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.util.UniqueIdGenerator;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * Created by jmarranz on 4/06/14.
 */
public class ItsNatDroidBrowserImpl implements ItsNatDroidBrowser
{
    public final static String USER_AGENT = "Apache-HttpClient/UNAVAILABLE (java 1.4) ItsNatDroidBrowser"; // Valor por defecto de DefaultHttpClient sin parámetros en emulador 4.0.3, le añadimos ItsNatDroidBrowser
    protected ItsNatDroidImpl parent;
    protected RequestPropertyMap requestPropertyMap;
    protected HttpParams httpParams;
    protected int connectTimeout = 6 * 1000;
    protected int readTimeout = 7 * 1000;
    protected HttpContext httpContext = new BasicHttpContext(); // Para las cookies del Apache Http Client (ej para las sesiones), la verdad es que no se si es multihilo pero no tengo más remedio
    protected Interpreter interp = new Interpreter(); // Global
    protected UniqueIdGenerator idGenerator = new UniqueIdGenerator();
    protected MapLight<String,ItsNatSessionImpl> sessionList = new MapLight<String, ItsNatSessionImpl>();
    protected int maxPagesInSession = 5;
    protected boolean sslSelfSignedAllowed = false; // Sólo poner a true en pruebas de desarrollo
    protected long fileCacheMaxSize = 100 * 1024; // 100Kb
    protected HttpFileCache httpFileCache = new HttpFileCache(fileCacheMaxSize);

    public ItsNatDroidBrowserImpl(ItsNatDroidImpl parent)
    {
        this.parent = parent;
        this.requestPropertyMap = getDefaultRequestPropertyMap();
        this.httpParams = getDefaultApacheHttpParams();
        // http://stackoverflow.com/questions/3587254/how-do-i-manage-cookies-with-httpclient-in-android-and-or-java
        CookieStore cookieStore = new BasicCookieStore();
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore); // httpContext tiene que ser global de otra manera las cookies no se retienen

        try
        {

            interp.set(NamespaceUtil.XMLNS_ANDROID_ALIAS, NamespaceUtil.XMLNS_ANDROID);
            // No definimos aquí set("itsNatDoc",null) o similar para poder definir métodos alert y toast
            // porque queda "itsNatDoc" como global y cualquier set() cambia valor global y por tanto ya no es local por Page


            // Funciones de utilidad que se reflejarán en los Interpreter hijos, pero así se interpretan una sola vez
            StringBuilder code = new StringBuilder();

            code.append("import org.itsnat.droid.*;");
            code.append("import org.itsnat.droid.event.*;");
            code.append("import android.view.*;");
            code.append("import android.widget.*;");
            code.append("import " + CustomFunction.class.getName() + ";");

            code.append("arr(a){return new Object[]{a};}");
            code.append("arr(a){return new Object[]{a};}");
            code.append("arr(a,b){return new Object[]{a,b};}");
            code.append("arr(a,b,c){return new Object[]{a,b,c};}");
            code.append("arr(a,b,c,d){return new Object[]{a,b,c,d};}");

            interp.eval(code.toString());
        }
        catch (EvalError ex) { throw new ItsNatDroidException(ex); } // No debería ocurrir
    }

    private static RequestPropertyMap getDefaultRequestPropertyMap()
    {
        RequestPropertyMap requestPropertyMap = new RequestPropertyMap();
        // Por ahora nada
        return requestPropertyMap;
    }

    private static HttpParams getDefaultApacheHttpParams()
    {
        // Configuración necesaria del Apache
        // Parámetros copiados de los parámetros por defecto de AndroidHttpClient.newInstance(...)
        // podríamos crear un AndroidHttpClient y coger los parámetros pero el problema es que "hay que usarlo".
        // http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.0.3_r1/android/net/http/AndroidHttpClient.java?av=f
        BasicHttpParams httpParams = new BasicHttpParams();
        httpParams.setParameter("http.useragent", "Apache-HttpClient/UNAVAILABLE (java 1.4)"); // Emulador 4.0.3  SE CAMBIARÁ más adelante
        httpParams.setIntParameter("http.socket.timeout", 60 * 1000);
        httpParams.setBooleanParameter("http.connection.stalecheck", false);
        httpParams.setIntParameter("http.connection.timeout", 60 * 1000);
        httpParams.setBooleanParameter("http.protocol.handle-redirects", false);
        httpParams.setIntParameter("http.socket.buffer-size", 8192);

        // AHORA cambiamos los que nos interesan para dejarlos por defecto
        httpParams.setParameter("http.useragent", USER_AGENT);  // Añadimos ItsNatDroidBrowser

        return httpParams;
    }

    public ItsNatDroid getItsNatDroid()
    {
        return getItsNatDroidImpl();
    }

    public ItsNatDroidImpl getItsNatDroidImpl()
    {
        return parent;
    }

    public ItsNatSessionImpl getItsNatSession(String stdSessionId,String sessionToken,String id)
    {
        ItsNatSessionImpl session = sessionList.get(stdSessionId);
        if (session == null || !session.getToken().equals(sessionToken))
        {
            // Si el token ha cambiado es que se ha recargado el servidor, hay que tener en cuenta que los ids por ej del cliente
            // están basados en un contador en memoria
            session = new ItsNatSessionImpl(this,stdSessionId,sessionToken,id);
            sessionList.put(stdSessionId,session);
        }

        return session;
    }

    public void disposeEmptySessions()
    {
        for(Iterator<Map.Entry<String,ItsNatSessionImpl>> it = sessionList.getEntryList().iterator(); it.hasNext(); )
        {
            Map.Entry<String,ItsNatSessionImpl> entry = it.next();
            if (entry.getValue().getPageCount() == 0) it.remove();
        }
    }

    public void disposeSessionIfEmpty(ItsNatSessionImpl session)
    {
        if (session.getPageCount() == 0) sessionList.remove(session.getStandardSessionId());
    }

    public HttpContext getHttpContext() { return httpContext; }

    public RequestPropertyMap getRequestPropertyMap()
    {
        return requestPropertyMap;
    }

    @Override
    public void addRequestProperty(String name, String value)
    {
        requestPropertyMap.addProperty(name, value);
    }

    @Override
    public void setRequestProperty(String name, String value)
    {
        requestPropertyMap.setProperty(name, value);
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
    public void setConnectTimeout(int timeoutMillis)
    {
        this.connectTimeout = timeoutMillis;
    }

    @Override
    public int getConnectTimeout()
    {
        return connectTimeout;
    }

    @Override
    public void setReadTimeout(int timeoutMillis)
    {
        this.readTimeout = timeoutMillis;
    }

    @Override
    public int getReadTimeout()
    {
        return readTimeout;
    }

    @Override
    public PageRequest createPageRequest()
    {
        return createPageRequestImpl();
    }

    public PageRequestImpl createPageRequestImpl()
    {
        return new PageRequestImpl(this);
    }

    public UniqueIdGenerator getUniqueIdGenerator()
    {
        return idGenerator;
    }

    public Interpreter getInterpreter()
    {
        return interp;
    }

    @Override
    public int getMaxPagesInSession()
    {
        return maxPagesInSession;
    }

    @Override
    public void setMaxPagesInSession(int maxPagesInSession)
    {
        this.maxPagesInSession = maxPagesInSession;
    }

    @Override
    public boolean isSSLSelfSignedAllowed()
    {
        return sslSelfSignedAllowed;
    }

    @Override
    public void setSSLSelfSignedAllowed(boolean enable)
    {
        this.sslSelfSignedAllowed = enable;
    }

    @Override
    public long getFileCacheMaxSize()
    {
        return fileCacheMaxSize;
    }

    @Override
    public void setFileCacheMaxSize(long size)
    {
        this.fileCacheMaxSize = size;
        this.httpFileCache.setMaxCacheSize(fileCacheMaxSize);
    }

    public HttpFileCache getHttpFileCache()
    {
        return httpFileCache;
    }
}
