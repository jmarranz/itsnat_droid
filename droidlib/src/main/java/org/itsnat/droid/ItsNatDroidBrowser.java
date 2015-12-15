package org.itsnat.droid;

/**
 * Created by jmarranz on 4/06/14.
 */
public interface ItsNatDroidBrowser
{
    public ItsNatDroid getItsNatDroid();
    public HttpParamMap getHttpParamMap();
    public void setHttpParamMap(HttpParamMap httpParamMap);
    public HttpParamMap createHttpParamMap();
    public PageRequest createPageRequest();
    public int getMaxPagesInSession();
    public void setMaxPagesInSession(int maxPages);
    public boolean isSSLSelfSignedAllowed();
    public void setSSLSelfSignedAllowed(boolean enable);
    public long getFileCacheMaxSize();
    public void setFileCacheMaxSize(long size);
}
