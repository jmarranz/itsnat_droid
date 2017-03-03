package org.itsnat.droid.impl.browser.serveritsnat.evtlistener;

import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocPageItsNatImpl;

/**
 * Created by jmarranz on 8/08/14.
 */
public class AttachedClientEventListener extends EventStfulListener
{
    public AttachedClientEventListener(ItsNatDocPageItsNatImpl parent, int commMode, long timeout)
    {
        super(parent,parent.getAttachType(),commMode,timeout);
    }
}
