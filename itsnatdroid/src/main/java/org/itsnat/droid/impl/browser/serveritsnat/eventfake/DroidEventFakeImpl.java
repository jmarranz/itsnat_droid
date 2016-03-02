package org.itsnat.droid.impl.browser.serveritsnat.eventfake;

import android.view.View;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.event.DroidEvent;


/**
 * Created by jmarranz on 24/12/2015.
 */
public class DroidEventFakeImpl implements DroidEvent
{
    protected String type;
    protected View currentTarget;

    public DroidEventFakeImpl(String type,View currentTarget)
    {
        this.type = type;
        this.currentTarget = currentTarget;
    }

    @Override
    public String getType()
    {
        return type;
    }

    @Override
    public View getCurrentTarget()
    {
        return currentTarget;
    }

    @Override
    public Object getExtraParam(String name)
    {
        throw new ItsNatDroidException("This method cannot be used in this context");
    }

    @Override
    public void setExtraParam(String name, Object value)
    {
        throw new ItsNatDroidException("This method cannot be used in this context");
    }
}
