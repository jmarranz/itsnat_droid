package org.itsnat.droid.impl.browser.serveritsnat.evtlistener;

import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocPageItsNatImpl;
import org.itsnat.droid.impl.browser.serveritsnat.event.EventGenericImpl;
import org.itsnat.droid.impl.util.NameValue;

import java.util.List;

/**
 * Created by jmarranz on 6/07/14.
 */
public abstract class EventGenericListener
{
    protected ItsNatDocPageItsNatImpl parent;
    protected String action;
    protected int commMode;
    protected long timeout;

    public EventGenericListener(ItsNatDocPageItsNatImpl parent, String action, int commMode, long timeout)
    {
        this.parent = parent;
        this.action = action;
        this.commMode = commMode;
        this.timeout = timeout;
    }

    public ItsNatDocPageItsNatImpl getItsNatDocItsNatImpl()
    {
        return parent;
    }

    public String getAction()
    {
        return action;
    }

    public int getCommMode()
    {
        return commMode;
    }

    public long getTimeout()
    {
        return timeout;
    }

    public void genParamURL(EventGenericImpl evt,List<NameValue> paramList)
    {
        paramList.add(new NameValue("itsnat_action",action));
    }
}
