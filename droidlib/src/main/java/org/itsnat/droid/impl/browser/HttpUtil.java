package org.itsnat.droid.impl.browser;

import org.apache.http.Header;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidServerResponseException;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.util.NameValue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by jmarranz on 23/05/14.
 */
public class HttpUtil
{
    public static HttpRequestResultOKImpl httpGet(String url,HttpRequestData httpRequestData,List<NameValue> paramList,String overrideMime) throws SocketTimeoutException
    {
        return httpAction("GET",url,httpRequestData,paramList,overrideMime);
    }

    public static HttpRequestResultOKImpl httpPost(String url,HttpRequestData httpRequestData,List<NameValue> paramList,String overrideMime) throws SocketTimeoutException
    {
        return httpAction("POST", url, httpRequestData, paramList, overrideMime);
    }

    public static HttpRequestResultOKImpl httpAction(String method,String url,HttpRequestData httpRequestData,List<NameValue> paramList,String overrideMime)
    {
        // http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
        // http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/net/URLConnection.java
        // http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/net/HttpURLConnection.java

        method = method.toUpperCase(); // Se debe de especificar que sea en mayúsculas pero por si acaso

        if ("GET".equals(method))
        {
            url += formURLQuery(url,paramList,true);
        }

        URI uriObj;
        URL urlObj;
        try
        {
            uriObj = new URI(url);
            urlObj = uriObj.toURL();
        }
        catch (URISyntaxException ex) { throw new ItsNatDroidException(ex); }
        catch (MalformedURLException ex) { throw new ItsNatDroidException(ex); }

        HttpURLConnection conn;

        try { conn = (HttpURLConnection)urlObj.openConnection(); }
        catch (IOException ex) { throw new ItsNatDroidException(ex); }

        // Headers:

        conn.setConnectTimeout(httpRequestData.connectTimeout);
        conn.setReadTimeout(httpRequestData.readTimeout);

        // Para evitar cacheados (en el caso de GET) por si acaso
        // http://stackoverflow.com/questions/49547/making-sure-a-web-page-is-not-cached-across-all-browsers
        conn.addRequestProperty("If-Modified-Since", "Wed, 15 Nov 1995 00:00:00 GMT");
        conn.addRequestProperty("Cache-Control", "no-store,no-httpFileCache,must-revalidate");
        conn.addRequestProperty("Pragma", "no-httpFileCache"); // HTTP 1.0.
        conn.addRequestProperty("Expires","0"); // Proxies.

        for(Map.Entry<String,List<String>> header : httpRequestData.requestPropertyMap.getPropertyMap().entrySet())
        {
            String name = header.getKey();
            for(String value : header.getValue())
                conn.addRequestProperty(name,value);
        }

        try { conn.setRequestMethod(method); }
        catch (ProtocolException ex) { throw new ItsNatDroidException(ex); }

        conn.setDoInput(true);

        if ("POST".equals(method))
        {
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            conn.setDoOutput(true);

            String postParams = formURLQuery(url,paramList,false);

            OutputStream os;
            try { os = conn.getOutputStream(); }
            catch (IOException ex) { throw new ItsNatDroidException(ex); }

            BufferedWriter writer;
            try { writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8")); }
            catch (UnsupportedEncodingException ex)
            {
                try { os.close(); } catch (IOException ex2) { throw new ItsNatDroidException(ex2); } // Nunca debería ocurrir
                throw new ItsNatDroidException(ex);
            }

            try
            {
                writer.write(postParams);
                writer.flush();
            }
            catch (IOException ex) { throw new ItsNatDroidException(ex); }
            finally
            {
                try { writer.close(); }
                catch (IOException ex) { throw new ItsNatDroidException(ex); } // Nunca debería ocurrir, cierra también OutputStream os
            }
        }

        // No hace falta llamar a connect() porque en POST basta enviar datos y en GET getResponseCode() hace efectiva la conexión
        int responseCode;
        try { responseCode = conn.getResponseCode(); }
        catch (IOException ex) { throw new ItsNatDroidException(ex); }

        return processResponse(url,conn,responseCode,httpRequestData.httpFileCache,overrideMime);
    }


    private static HttpRequestResultOKImpl processResponse(String url,HttpURLConnection conn,int responseCode,HttpFileCache httpFileCache,String overrideMime)
    {
        String[] mimeTypeRes = new String[1];
        String[] encodingRes = new String[1];

        getMimeTypeEncoding(conn, mimeTypeRes, encodingRes);

        if (!MiscUtil.isEmpty(overrideMime)) mimeTypeRes[0] = overrideMime;

        HttpRequestResultImpl result = HttpRequestResultImpl.createHttpRequestResult(url, conn, httpFileCache, mimeTypeRes[0], encodingRes[0]);

        if (result instanceof HttpRequestResultFailImpl)
        {
            throw new ItsNatDroidServerResponseException(result);
        }

        return (HttpRequestResultOKImpl)result;
    }

    private static void getMimeTypeEncoding(HttpURLConnection conn, String[] mimeType, String[] encoding)
    {
        mimeType[0] = "android/layout"; // Por defecto y por si acaso
        encoding[0] = "UTF-8";

        String contentType = conn.getContentType();
        if (contentType == null) // Por si acaso
            return;

        // Ej: Content-Type: android/layout;charset=UTF-8

        int posCharset = contentType.lastIndexOf(';');
        if (posCharset != -1)
        {
            mimeType[0] = contentType.substring(0,posCharset);
            String charset = contentType.substring(posCharset + 1); // Ej charset=UTF-8
            int posEq = charset.indexOf('=');
            encoding[0] = charset.substring(posEq + 1);
        }
        else
        {
            mimeType[0] = contentType;
            // Usar conn.getContentEncoding() por si acaso ????
        }
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
                    query.append(URLEncoder.encode(param.getName(), "UTF-8"));
                    query.append("=");
                    query.append(URLEncoder.encode(param.getValue(), "UTF-8"));
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

    public static String composeAbsoluteURL(String src,String pageURL)
    {
        URI uri = null;
        try { uri = new URI(src); } catch (URISyntaxException ex) { throw new ItsNatDroidException(ex); }

        String scheme = uri.getScheme();
        if (scheme == null)
        {
            if (src.startsWith("/"))
            {
                // Path absoluto, tenemos que formar: scheme://authority + src
                URL url;
                try { url = new URL(pageURL); }
                catch (MalformedURLException ex) { throw new ItsNatDroidException(ex); }
                src = url.getProtocol() + "://" + url.getAuthority() + src;
            }
            else
            {
                int pos = pageURL.lastIndexOf('/');
                if (pos < pageURL.length() - 1) // El / no está en el final
                    pageURL = pageURL.substring(0, pos + 1); // Quitamos así el servlet, el JSP etc que generó la página
                // Ahora pageURL termina en '/'
                src = pageURL.substring(0, pos + 1) + src;
            }
        }
        else if (!scheme.equals("http") && !scheme.equals("https"))
            throw new ItsNatDroidException("Scheme not supported: " + scheme);

        return src;
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