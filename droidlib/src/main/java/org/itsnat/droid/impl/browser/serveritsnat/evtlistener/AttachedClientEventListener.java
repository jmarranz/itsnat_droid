package org.itsnat.droid.impl.browser.serveritsnat.evtlistener;

import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocItsNatImpl;

/**
 * Created by jmarranz on 8/08/14.
 */
public class AttachedClientEventListener extends EventStfulListener
{
    public AttachedClientEventListener(ItsNatDocItsNatImpl parent,int commMode, long timeout)
    {
        super(parent,parent.getAttachType(),commMode,timeout);
    }
}
