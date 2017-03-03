package org.itsnat.droid.impl.browser.serveritsnat.event;

import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocPageItsNatImpl;

/**
 * Created by jmarranz on 8/08/14.
 */
public class AttachedClientTimerRefreshEventImpl extends AttachedClientEventImpl
{
    protected long interval;

    public AttachedClientTimerRefreshEventImpl(ItsNatDocPageItsNatImpl parent, int interval, int commMode, long timeout)
    {
        super(parent,commMode,timeout);
        this.interval = interval;
    }

    public void onEventReturned()
    {
        super.onEventReturned();

        ItsNatDocPageItsNatImpl itsNatDoc = getAttachedClientEventListener().getItsNatDocItsNatImpl();
        itsNatDoc.getHandler().postDelayed(itsNatDoc.getAttachTimerRefreshCallback(), interval);
    }
}
