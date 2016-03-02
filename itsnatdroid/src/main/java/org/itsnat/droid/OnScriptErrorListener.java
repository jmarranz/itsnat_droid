package org.itsnat.droid;

/**
 * Created by jmarranz on 11/07/14.
 */
public interface OnScriptErrorListener
{
    public void onError(Page page, String code, Exception ex, Object context);
}
