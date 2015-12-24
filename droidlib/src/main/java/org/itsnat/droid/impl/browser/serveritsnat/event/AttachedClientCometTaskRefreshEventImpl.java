package org.itsnat.droid.impl.browser.serveritsnat.event;

import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocImpl;
import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocItsNatImpl;
import org.itsnat.droid.impl.util.NameValue;

import java.util.List;

/**
 * Created by jmarranz on 8/08/14.
 */
public class AttachedClientCometTaskRefreshEventImpl extends AttachedClientEventImpl
{
    protected String listenerId;

    public AttachedClientCometTaskRefreshEventImpl(ItsNatDocItsNatImpl parent,String listenerId, int commMode, long timeout)
    {
        super(parent,commMode,timeout);
        this.listenerId = listenerId;
    }

    @Override
    public List<NameValue> genParamURL()
    {
        List<NameValue> params = super.genParamURL();

        params.add(new NameValue("itsnat_listener_id",listenerId));

        return params;
    }

}
