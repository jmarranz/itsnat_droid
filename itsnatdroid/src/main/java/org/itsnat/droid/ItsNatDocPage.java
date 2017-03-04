package org.itsnat.droid;

import android.view.View;

import org.itsnat.droid.event.EventStateless;
import org.itsnat.droid.event.UserEvent;

/**
 *
 * Created by jmarranz on 12/06/14.
 */
public interface ItsNatDocPage extends ItsNatDoc
{
    public Page getPage();

    public ItsNatView getItsNatView(View view);

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

