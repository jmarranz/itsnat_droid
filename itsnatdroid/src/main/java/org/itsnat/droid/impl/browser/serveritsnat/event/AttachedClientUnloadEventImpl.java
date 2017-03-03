package org.itsnat.droid.impl.browser.serveritsnat.event;

import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocPageItsNatImpl;
import org.itsnat.droid.impl.util.NameValue;

import java.util.List;

/**
 * Created by jmarranz on 8/08/14.
 */
public class AttachedClientUnloadEventImpl extends AttachedClientEventImpl
{
    public AttachedClientUnloadEventImpl(ItsNatDocPageItsNatImpl parent, int commMode, long timeout)
    {
        super(parent,commMode,timeout);
    }

    public boolean isIgnoreHold()
    {
        return true; // Es un unload
    }

    @Override
    public List<NameValue> genParamURL()
    {
        List<NameValue> params = super.genParamURL();

        params.add(new NameValue("itsnat_unload",true));

        return params;
    }
}
