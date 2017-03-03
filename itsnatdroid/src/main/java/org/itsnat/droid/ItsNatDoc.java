package org.itsnat.droid;

import android.content.Context;

/**
 * Created by jmarranz on 03/03/2017.
 */

public interface ItsNatDoc
{
    public UserData getUserData();
    public Context getContext();
    public ItsNatResources getItsNatResources();

    public void postDelayed(Runnable task, long delay);

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
}
