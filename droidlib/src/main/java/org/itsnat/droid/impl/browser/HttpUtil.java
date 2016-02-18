package org.itsnat.droid.impl.browser;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidServerResponseException;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.util.NameValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by jmarranz on 23/05/14.
 */
public class HttpUtil
{
    public static HttpRequestResultOKImpl httpGet(String url,HttpRequestData httpRequestData,List<NameValue> paramList,String overrideMime)
    {
        return httpAction("GET",url,httpRequestData,paramList,overrideMime);
    }

    public static HttpRequestResultOKImpl httpPost(String url,HttpRequestData httpRequestData,List<NameValue> paramList,String overrideMime)
    {
        return httpAction("POST", url, httpRequestData, paramList, overrideMime);
    }

    public static HttpRequestResultOKImpl httpAction(String method,String url,HttpRequestData httpRequestData,List<NameValue> paramList,String overrideMime)
    {
        URI uri;
        try { uri = new URI(url); }
        catch (URISyntaxException ex) { throw new ItsNatDroidException(ex); }

        HttpParams httpParams = httpRequestData.getHttpParams();

        int connTimeout = httpRequestData.getConnectTimeout();
        int readTimeout = httpRequestData.getReadTimeout();

        httpParams.setIntParameter("http.connection.timeout", connTimeout);
        httpParams.setIntParameter("http.socket.timeout", readTimeout);

        HttpClient httpClient = createHttpClient(uri.getScheme(),httpRequestData.isSslSelfSignedAllowed(),httpParams);

        HttpUriRequest httpUriRequest = null;

        method = method.toUpperCase(); // Se especifica que sea en mayúsculas pero por si acaso
        if ("POST".equals(method) || "PUT".equals(method)) // PATCH no está implementado (sería HttpPatch)
        {
            if ("POST".equals(method))
                httpUriRequest = new HttpPost(url);
            else // PUT
                httpUriRequest = new HttpPut(url); // http://stackoverflow.com/questions/3649814/android-httpput-example-code
            // httpUriRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
            try
            {
                List<NameValuePair> paramListApache = new ArrayList<NameValuePair>(paramList.size()); // Creo que no se admite que sea nulo
                if (paramList != null)
                {
                    for (NameValue nameValue : paramList)
                    {
                        String name = nameValue.getName();
                        String value = nameValue.getValue().toString();
                        paramListApache.add(new BasicNameValuePair(name, value));
                    }
                }

                ((HttpEntityEnclosingRequest)httpUriRequest).setEntity(new UrlEncodedFormEntity(paramListApache,"UTF-8"));
            }
            catch (UnsupportedEncodingException ex) { throw new ItsNatDroidException(ex); }
        }
        else
        {
            if (paramList != null && paramList.size() > 0)
            {
                List<NameValuePair> paramListApache = new ArrayList<NameValuePair>(paramList.size());
                for(NameValue nameValue : paramList)
                {
                    String name = nameValue.getName();
                    String value = nameValue.getValue().toString();
                    paramListApache.add(new BasicNameValuePair(name,value));
                }

                // http://stackoverflow.com/questions/2959316/how-to-add-parameters-to-a-http-get-request-in-android
                String paramString = URLEncodedUtils.format(paramListApache, "UTF-8");
                int pos = url.lastIndexOf('?');
                if (pos != -1) // Tiene ?
                {
                    if (!url.endsWith("?")) url += '&'; // Tiene parámetros
                }
                else // No tiene ?
                {
                    url += '?';
                }
                url += paramString;
            }

            if ("GET".equals(method)) { httpUriRequest = new HttpGet(url); }
            else if ("DELETE".equals(method)) { httpUriRequest = new HttpDelete(url); }
            else if ("HEAD".equals(method)) { httpUriRequest = new HttpHead(url); }
            else if ("OPTIONS".equals(method)) { httpUriRequest = new HttpOptions(url); }
            else if ("TRACE".equals(method)) { httpUriRequest = new HttpTrace(url); }
            else throw new ItsNatDroidException("Unsupported HTTP method: " + method);
        }


        HttpResponse httpResponse = execute(httpClient,httpUriRequest,httpRequestData.getHttpContext(),httpRequestData.getRequestPropertyMap());
        return processResponse(url,httpResponse,httpRequestData.getHttpFileCache(),overrideMime);
    }

    private static HttpResponse execute(HttpClient httpClient,HttpUriRequest httpUriRequest,HttpContext httpContext,RequestPropertyMap requestPropertyMap)
    {
        try
        {
            // Para evitar cacheados (en el caso de GET) por si acaso
            // http://stackoverflow.com/questions/49547/making-sure-a-web-page-is-not-cached-across-all-browsers
            httpUriRequest.setHeader("If-Modified-Since","Wed, 15 Nov 1995 00:00:00 GMT");
            httpUriRequest.setHeader("Cache-Control","no-store,no-httpFileCache,must-revalidate");
            httpUriRequest.setHeader("Pragma", "no-httpFileCache"); // HTTP 1.0.
            httpUriRequest.setHeader("Expires", "0"); // Proxies.

            for(Map.Entry<String,List<String>> header : requestPropertyMap.getPropertyMap().entrySet())
            {
                String name = header.getKey();
                List<String> valueList = header.getValue();
                for(String value : valueList)
                    httpUriRequest.addHeader(name, value);
            }

            HttpResponse response = httpClient.execute(httpUriRequest, httpContext);
            return response;
        }
        catch(SocketTimeoutException ex)
        { throw new ItsNatDroidException(ex); }   // Hay un caso en que se ejecuta fireEventMonitors de forma específica al detectar esta excepción
        catch(ClientProtocolException ex) { throw new ItsNatDroidException(ex); }
        catch(IOException ex) { throw new ItsNatDroidException(ex); }
    }

    private static HttpRequestResultOKImpl processResponse(String url,HttpResponse httpResponse,HttpFileCache httpFileCache,String overrideMime)
    {
        // Get hold of the response entity
        HttpEntity entity = httpResponse.getEntity();
        // If the response does not enclose an entity, there is no need
        // to worry about connection release

        if (entity == null) throw MiscUtil.internalError(); // null es muy raro incluso en caso de error

        String[] mimeTypeRes = new String[1];
        String[] encodingRes = new String[1];

        getMimeTypeEncoding(httpResponse, mimeTypeRes, encodingRes);

        if (!MiscUtil.isEmpty(overrideMime)) mimeTypeRes[0] = overrideMime;

        InputStream input = null;
        try { input = entity.getContent(); } // Interesa incluso cuando hay error (statusCode != 200) porque obtenemos el texto del error
        catch (IOException ex) { throw new ItsNatDroidException(ex); }

        HttpRequestResultImpl result = HttpRequestResultImpl.createHttpRequestResult(url,httpResponse,input, httpFileCache, mimeTypeRes[0], encodingRes[0]);

        if (result instanceof HttpRequestResultFailImpl)
        {
            throw new ItsNatDroidServerResponseException(result);
        }

        return (HttpRequestResultOKImpl)result;
    }

    public static HttpClient createHttpClient(String scheme,boolean sslSelfSignedAllowed,HttpParams httpParams)
    {
        if (sslSelfSignedAllowed && scheme.equals("https"))
            return getHttpClientSSLSelfSignedAllowed(httpParams);
        else
            return getHttpClientThreadSafe(httpParams);
    }

    public static HttpClient getHttpClientSSLSelfSignedAllowed(HttpParams params)
    {
        // URLs para probar: "https://www.pcwebshop.co.uk/" "https://mms.nw.ru/"
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SSLSocketFactoryForSelfSigned(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            //HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            //HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        }
        catch (KeyStoreException ex) { throw new ItsNatDroidException(ex); }
        catch (NoSuchAlgorithmException ex) { throw new ItsNatDroidException(ex); }
        catch (IOException ex) { throw new ItsNatDroidException(ex); }
        catch (CertificateException ex) { throw new ItsNatDroidException(ex); }
        catch (KeyManagementException ex) { throw new ItsNatDroidException(ex); }
        catch (UnrecoverableKeyException ex) { throw new ItsNatDroidException(ex); }
    }


    private static DefaultHttpClient getHttpClientThreadSafe(HttpParams httpParams)
    {
        // Evitamos el error aleatorio:  java.lang.IllegalStateException: No wrapped connection
        // http://www.androider.me/2013/11/solve-one-issue-because-of-thread-safe.html
        // https://groups.google.com/forum/#!topic/android-developers/GUnCMjCnKKQ
        // http://stackoverflow.com/questions/10795591/releasing-connection-in-android

        DefaultHttpClient client = new DefaultHttpClient(httpParams);

        ClientConnectionManager mgr = client.getConnectionManager();

        HttpParams params = client.getParams();

        client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,mgr.getSchemeRegistry()), params);

        return client;
    }


    private static void getMimeTypeEncoding(HttpResponse httpResponse, String[] mimeType, String[] encoding)
    {
        Header[] contentTypes = httpResponse.getHeaders("Content-Type"); // Internamente ignora mayúsculas y minúsculas, no hay que preocuparse
        if (contentTypes != null && contentTypes.length > 0)
        {
            // Ej: Content-Type: android/layout;charset=UTF-8
            HeaderElement[] elems = contentTypes[0].getElements(); // https://hc.apache.org/httpclient-3.x/apidocs/org/apache/commons/httpclient/HeaderElement.html
            for (HeaderElement elem : elems)
            {
                mimeType[0] = elem.getName();
                NameValuePair[] params = elem.getParameters();
                for (NameValuePair param : params)
                {
                    String name = param.getName();
                    if (name.equalsIgnoreCase("charset"))
                    {
                        encoding[0] = param.getValue();
                        break;
                    }
                }
                if (encoding != null) break;
            }
        }

        if (mimeType[0] == null) mimeType[0] = "android/layout"; // Por si acaso
        if (encoding[0] == null) encoding[0] = "UTF-8"; // Por si acaso

    }

    private static String formURLQuery(String url,List<NameValue> paramList,boolean get)
    {
        if (paramList != null && paramList.size() > 0)
        {
            StringBuilder query = new StringBuilder();

            if (get)
            {
                int pos = url.indexOf('?');
                if (pos != -1) // Tiene ?
                {
                    if (!url.endsWith("?")) query.append('&'); // Tiene ya parámetros y tenemos que añadir más
                }
            }

            int i = 0;
            for(NameValue param : paramList)
            {
                try
                {
                    String name = param.getName();
                    Object value = param.getValue();
                    query.append(URLEncoder.encode(name, "UTF-8"));
                    query.append("=");
                    if (value != null)
                        query.append(URLEncoder.encode(value.toString(), "UTF-8"));
                    if (i < paramList.size() - 1)
                        query.append('&'); // Hay más parámetros

                    i++;
                }
                catch (UnsupportedEncodingException ex)
                {
                    throw new ItsNatDroidException(ex);
                }
            }
            return query.toString();
        }
        else
        {
            return "";
        }
    }

    public static String composeAbsoluteURL(String src,String pageURLStr)
    {
        String absURL;

        URI uri;
        try { uri = new URI(src); }
        catch (URISyntaxException ex)
        { throw new ItsNatDroidException(ex); }

        String scheme = uri.getScheme();
        if (scheme == null)
        {
            if (src.equals("")) // Ejemplo: <item name="android:paddingRight">@remote:dimen/:test_dimen_paddingRight</item> referenciado en un archivo XML values
            {
                absURL = pageURLStr;
            }
            else if (src.startsWith("/"))
            {
                // Path absoluto, tenemos que formar: scheme://authority + src
                URL pageURL;
                try { pageURL = new URL(pageURLStr); }
                catch (MalformedURLException ex) { throw new ItsNatDroidException(ex); }
                absURL = pageURL.getProtocol() + "://" + pageURL.getAuthority() + src;
            }
            else
            {
                int pos = pageURLStr.lastIndexOf('/');
                if (pos < pageURLStr.length() - 1) // El / no está en el final
                    pageURLStr = pageURLStr.substring(0, pos + 1); // Quitamos así el servlet, el JSP etc que generó la página
                // Ahora pageURLStr termina en '/'
                absURL = pageURLStr.substring(0, pos + 1) + src;
            }
        }
        else
        {
            // Path absoluto, nada que componer
            if (!scheme.equals("http") && !scheme.equals("https")) throw new ItsNatDroidException("Scheme not supported: " + scheme);
            absURL = src;
        }

        return absURL;
    }

    public static String getBasePathOfURL(String urlStr)
    {
        URL u = null;
        try { u = new URL(urlStr); }
        catch (MalformedURLException ex) { throw new ItsNatDroidException(ex); }

        // Vale, sí, este código está basado en el código fuente de java.net.URLStreamHandler.toExternalForm()

        // pre-compute length of StringBuilder
        int len = u.getProtocol().length() + 1;
        if (u.getAuthority() != null && u.getAuthority().length() > 0)
            len += 2 + u.getAuthority().length();
        if (u.getPath() != null) {
            len += u.getPath().length();
        }
        /*
        if (u.getQuery() != null) {
            len += 1 + u.getQuery().length();
        }
        if (u.getRef() != null)
            len += 1 + u.getRef().length();
        */
        StringBuilder result = new StringBuilder(len);
        result.append(u.getProtocol());
        result.append(":");
        if (u.getAuthority() != null && u.getAuthority().length() > 0) {
            result.append("//");
            result.append(u.getAuthority());
        }
        if (u.getPath() != null) {
            result.append(u.getPath());
        }
        /*
        if (u.getQuery() != null) {
            result.append('?');
            result.append(u.getQuery());
        }
        if (u.getRef() != null) {
            result.append("#");
            result.append(u.getRef());
        }
        */
        return result.toString();
    }

    private static class SSLSocketFactoryForSelfSigned extends SSLSocketFactory
    {
        private SSLContext sslContext = SSLContext.getInstance("TLS");

        public SSLSocketFactoryForSelfSigned(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
        {
            super(truststore);

            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException { }
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException { }
                public X509Certificate[] getAcceptedIssuers() { return null; }
            };

            sslContext.init(null, new TrustManager[] { tm }, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException
        {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }

    private static String convertStreamToString_NO_SE_USA(InputStream is)
    {
    /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
        }
        catch (IOException ex) { throw new ItsNatDroidException(ex); }
        finally
        {
            try
            {
                is.close();
            }
            catch (IOException ex) { throw new ItsNatDroidException(ex); }
        }
        return sb.toString();
    }

}