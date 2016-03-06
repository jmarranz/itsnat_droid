package org.itsnat.droid;

import android.app.Activity;
import android.content.res.Configuration;

/**
 * Created by jmarranz on 3/05/14.
 */
public interface ItsNatDroid
{
    public ItsNatDroidBrowser createItsNatDroidBrowser();
    public InflateLayoutRequest createInflateLayoutRequest();
    public String getVersionName();
    public int getVersionCode();
    public void onConfigurationChanged(Activity activity, Configuration newConfig);
}
