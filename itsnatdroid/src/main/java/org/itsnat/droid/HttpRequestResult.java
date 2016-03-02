package org.itsnat.droid;

/**
 * Created by jmarranz on 10/10/14.
 */
public interface HttpRequestResult
{
    public String getHeaderField(String key);
    public long getHeaderFieldDate(String field, long defaultValue);
    public int getHeaderFieldInt(String field, int defaultValue);

    public String getMimeType();
    public String getEncoding();
    public byte[] getResponseByteArray();
    public String getResponseText();
}
