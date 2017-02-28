package org.itsnat.droid;

import android.content.Context;
import android.view.View;

/**
 * Created by jmarranz on 16/06/14.
 */
public interface InflatedLayout
{
    public Context getContext();
    public ItsNatDroid getItsNatDroid();
    public ItsNatDroidBrowser getItsNatDroidBrowser();
    public View getRootView();
    public View findViewByXMLId(String id);

    public void alert(Object value);
    public void alert(String title, Object value);
    public void toast(Object value, int duration);
    public void toast(Object value);

    public Object eval(String code);

    public void set(String name, Object value );
    public void set(String name, long value);
    public void set(String name, int value);
    public void set(String name, double value);
    public void set(String name, float value);
    public void set(String name, boolean value);

    public void unset(String name);

    public UserData getUserData();
    public ItsNatResources getItsNatResources();
}
