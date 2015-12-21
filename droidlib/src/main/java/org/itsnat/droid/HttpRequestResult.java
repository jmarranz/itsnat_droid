package org.itsnat.droid;

import org.apache.http.Header;
import org.apache.http.StatusLine;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 10/10/14.
 */
public interface HttpRequestResult
{
    public String getMimeType();
    public String getEncoding();
    public HttpURLConnection getHttpURLConnection();
    public byte[] getResponseByteArray();
    public String getResponseText();
}
