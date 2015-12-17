package org.itsnat.droid;

import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 4/06/14.
 */
public interface ItsNatDroidBrowser
{
    public ItsNatDroid getItsNatDroid();
    public void addRequestProperty(String name, String value);
    public void setRequestProperty(String name, String value);
    public boolean removeProperty(String name);
    public String getRequestProperty(String name);
    public void setConnectTimeout(int timeoutMillis);
    public int getConnectTimeout();
    public void setReadTimeout(int timeoutMillis);
    public int getReadTimeout();
    public Map<String, List<String>> getRequestProperties();
    public PageRequest createPageRequest();
    public int getMaxPagesInSession();
    public void setMaxPagesInSession(int maxPages);
    public boolean isSSLSelfSignedAllowed();
    public void setSSLSelfSignedAllowed(boolean enable);
    public long getFileCacheMaxSize();
    public void setFileCacheMaxSize(long size);
}
