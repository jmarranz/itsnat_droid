package org.itsnat.droid.impl.browser.serveritsnat.event;

import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocPageItsNatImpl;
import org.itsnat.droid.impl.browser.serveritsnat.evtlistener.AttachedClientEventListener;

/**
 * Created by jmarranz on 7/07/14.
 */
public abstract class AttachedClientEventImpl extends EventStfulImpl
{
    public AttachedClientEventImpl(ItsNatDocPageItsNatImpl parent, int commMode, long timeout)
    {
        super(new AttachedClientEventListener(parent,commMode,timeout));
    }

    public AttachedClientEventListener getAttachedClientEventListener()
    {
        return (AttachedClientEventListener)listener;
    }

    @Override
    public void saveEvent()
    {
    }

}

