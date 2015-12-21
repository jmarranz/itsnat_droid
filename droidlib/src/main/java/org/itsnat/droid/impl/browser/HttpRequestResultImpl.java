package org.itsnat.droid.impl.browser;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.itsnat.droid.HttpRequestResult;

import java.io.InputStream;
import java.util.Date;

/**
 * Created by jmarranz on 16/07/14.
 */
public abstract class HttpRequestResultImpl implements HttpRequestResult
{
    private String url;
    protected HttpResponse httpResponse;
    protected byte[] responseByteArray;
    private String mimeType;
    private String encoding;
    protected String responseText;

    protected HttpRequestResultImpl(String url,HttpResponse httpResponse,String mimeType, String encoding)
    {
        this.url = url;
        this.httpResponse = httpResponse;
        this.mimeType = mimeType;
        this.encoding = encoding;
    }

    public static HttpRequestResultImpl createHttpRequestResult(String url,HttpResponse httpResponse,InputStream input,HttpFileCache httpFileCache,String mimeType, String encoding)
    {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 200)
            return new HttpRequestResultOKImpl(url,httpResponse,input,httpFileCache,mimeType,encoding);
        else
            return new HttpRequestResultFailImpl(url,httpResponse,input,mimeType,encoding);
    }

    public String getUrl()
    {
        return url;
    }

    @Override
    public String getHeaderField(String key)
    {
        Header[] headersFound = getResponseHeaders(key);
        if (headersFound == null)
            return null;
        return headersFound[0].getValue();
    }

    @SuppressWarnings("deprecation")
    public long getHeaderFieldDate(String field, long defaultValue) {
        String date = getHeaderField(field);
        if (date == null) {
            return defaultValue;
        }
        try {
            return Date.parse(date); // TODO: use HttpDate.parse()
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public int getHeaderFieldInt(String field, int defaultValue) {
        try {
            return Integer.parseInt(getHeaderField(field));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    protected Header[] getResponseHeaders(String name)
    {
        Header[] headersFound = httpResponse.getHeaders(name);
        if (headersFound == null || headersFound.length == 0)
            return null;
        return headersFound;
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