package org.itsnat.droid.impl.browser.serveritsnat.evtlistener;

import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocPageItsNatImpl;
import org.itsnat.droid.impl.browser.serveritsnat.event.EventGenericImpl;
import org.itsnat.droid.impl.util.NameValue;

import java.util.List;

/**
 * Created by jmarranz on 6/07/14.
 */
public class EventStatelessListener extends EventGenericListener
{
    public EventStatelessListener(ItsNatDocPageItsNatImpl parent, int commMode, long timeout)
    {
        super(parent,"event_stateless",commMode, timeout);
    }

    @Override
    public void genParamURL(EventGenericImpl evt,List<NameValue> paramList)
    {
        super.genParamURL(evt, paramList);
        // Recuerda que al ser stateless tenemos que enviar el commMode y el eventTimeout, de hecho los proporciona el usuario desde el cliente
        paramList.add(new NameValue("itsnat_commMode", commMode));
        paramList.add(new NameValue("itsnat_eventTimeout", timeout));
    }
}
