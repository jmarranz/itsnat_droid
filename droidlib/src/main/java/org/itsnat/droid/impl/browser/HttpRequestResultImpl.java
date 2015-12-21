package org.itsnat.droid.impl.browser;

import org.itsnat.droid.HttpRequestResult;
import org.itsnat.droid.ItsNatDroidException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by jmarranz on 16/07/14.
 */
public abstract class HttpRequestResultImpl implements HttpRequestResult
{
    private String url;
    protected HttpURLConnection conn;
    protected byte[] responseByteArray;
    private String mimeType;
    private String encoding;
    protected String responseText;


    protected HttpRequestResultImpl(String url,HttpURLConnection conn,String mimeType, String encoding)
    {
        this.url = url;
        this.conn = conn;
        this.mimeType = mimeType;
        this.encoding = encoding;
    }

    public static HttpRequestResultImpl createHttpRequestResult(String url,HttpURLConnection httpURLConnection,HttpFileCache httpFileCache,String mimeType, String encoding)
    {
        if (isStatusOK(httpURLConnection))
            return new HttpRequestResultOKImpl(url,httpURLConnection,httpFileCache,mimeType,encoding);
        else
            return new HttpRequestResultFailImpl(url,httpURLConnection,mimeType,encoding);
    }

    public String getUrl()
    {
        return url;
    }


    public boolean isStatusOK()
    {
        return isStatusOK(conn);
    }

    public static boolean isStatusOK(HttpURLConnection httpURLConnection)
    {
        try { return httpURLConnection.getResponseCode() == 200; }
        catch (IOException ex) { throw new ItsNatDroidException(ex); }
    }

    @Override
    public String getMimeType()
    {
        return mimeType;
    }

    @Override
    public String getEncoding()
    {
        return encoding;
    }

    @Override
    public HttpURLConnection getHttpURLConnection()
    {
        return conn;
    }

    @Override
    public byte[] getResponseByteArray()
    {
        return responseByteArray;
    }

    @Override
    public String getResponseText()
    {
        return responseText;
    }

}