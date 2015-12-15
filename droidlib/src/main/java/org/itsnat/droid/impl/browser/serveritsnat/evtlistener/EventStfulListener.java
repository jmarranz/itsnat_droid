package org.itsnat.droid.impl.browser.serveritsnat.evtlistener;

import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocImpl;
import org.itsnat.droid.impl.browser.serveritsnat.event.EventGenericImpl;
import org.itsnat.droid.impl.util.NameValue;

import java.util.List;

/**
 * Created by jmarranz on 6/07/14.
 */
public abstract class EventStfulListener extends EventGenericListener
{
    protected String eventType;

    public EventStfulListener(ItsNatDocImpl parent,String eventType, int commMode, long timeout)
    {
        super(parent,"event",commMode, timeout);

        this.eventType = eventType;
    }

    public String getEventType()
    {
        return eventType;
    }

    @Override
    public void genParamURL(EventGenericImpl evt,List<NameValue> paramList)
    {
        super.genParamURL(evt, paramList);
        paramList.add(new NameValue("itsnat_eventType", "" + getEventType()));
    }
}
