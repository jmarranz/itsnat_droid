package org.itsnat.droid;

import android.view.View;

import org.itsnat.droid.event.EventStateless;
import org.itsnat.droid.event.UserEvent;

import bsh.EvalError;
import bsh.Primitive;

/**
 *
 * Created by jmarranz on 12/06/14.
 */
public interface ItsNatDoc
{
    public Page getPage();

    public ItsNatResources getItsNatResources();
    public View getRootView();
    public View findViewByXMLId(String id);
    public int getResourceIdentifier(String name);
    public int getResourceIdentifier(String name, String defType, String defPackage);
    public ItsNatView getItsNatView(View view);

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

    public GenericHttpClient createGenericHttpClient();

    public void addEventMonitor(EventMonitor monitor);
    public boolean removeEventMonitor(EventMonitor monitor);
    public void setEnableEventMonitors(boolean value);

    public UserEvent createUserEvent(String name);
    public void dispatchUserEvent(View currTarget, UserEvent evt);
    public void fireUserEvent(View currTarget, String name);

    public EventStateless createEventStateless();
    public void dispatchEventStateless(EventStateless evt, int commMode, long timeout);

    public void appendFragment(View parentView, String markup);
    public void insertFragment(View parentView, String markup, View viewRef);
}

