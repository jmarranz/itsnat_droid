package org.itsnat.droid;

import android.content.Context;

import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 4/06/14.
 */
public interface Page
{
    public ItsNatDroidBrowser getItsNatDroidBrowser();
    public String getURL();
    public String getId();
    public Context getContext();
    public Map<String, List<String>> getRequestProperties();
    public int getConnectTimeout();
    public int getReadTimeout();
    public HttpRequestResult getHttpRequestResult();
    public ItsNatSession getItsNatSession();
    public UserData getUserData();
    public ItsNatDocPage getItsNatDoc();
    public void setOnScriptErrorListener(OnScriptErrorListener listener);
    public void setOnEventErrorListener(OnEventErrorListener listener);
    public void setOnServerStateLostListener(OnServerStateLostListener listener);
    public void setOnHttpRequestErrorListener(OnHttpRequestErrorListener listener);
    public PageRequest reusePageRequest();
    public boolean isDisposed();
    public void dispose();
}
