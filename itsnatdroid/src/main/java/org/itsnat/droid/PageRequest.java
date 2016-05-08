package org.itsnat.droid;

import android.content.Context;

import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 5/06/14.
 */
public interface PageRequest
{
    public PageRequest setContext(Context ctx);
    public PageRequest setBitmapDensityReference(int density);
    public PageRequest setClientErrorMode(int errorMode);
    public PageRequest setOnPageLoadListener(OnPageLoadListener listener);
    public PageRequest setOnPageLoadErrorListener(OnPageLoadErrorListener listener);
    public PageRequest setOnScriptErrorListener(OnScriptErrorListener listener);
    public PageRequest setAttrLayoutInflaterListener(AttrLayoutInflaterListener listener);
    public PageRequest setAttrDrawableInflaterListener(AttrDrawableInflaterListener listener);
    public PageRequest setAttrAnimationInflaterListener(AttrAnimationInflaterListener listener);
    public PageRequest setAttrAnimatorInflaterListener(AttrAnimatorInflaterListener listener);
    public PageRequest setAttrInterpolatorInflaterListener(AttrInterpolatorInflaterListener attrInterpolatorInflaterListener);
    public PageRequest setAttrResourceInflaterListener(AttrResourceInflaterListener attrResourcesInflaterListener);
    public PageRequest addRequestProperty(String name, String value);
    public PageRequest setRequestProperty(String name, String value);
    public boolean removeProperty(String name);
    public String getRequestProperty(String name);
    public Map<String, List<String>> getRequestProperties();
    public PageRequest setConnectTimeout(int timeoutMillis);
    public int getConnectTimeout();
    public PageRequest setReadTimeout(int timeoutMillis);
    public int getReadTimeout();
    public PageRequest setSynchronous(boolean sync);
    public PageRequest setURL(String url);
    public void execute();
}
