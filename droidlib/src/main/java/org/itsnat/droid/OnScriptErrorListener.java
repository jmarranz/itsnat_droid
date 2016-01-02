package org.itsnat.droid;

import org.itsnat.droid.event.Event;

/**
 * Created by jmarranz on 11/07/14.
 */
public interface OnScriptErrorListener
{
    public void onError(String code,Exception ex,Object context);
}
