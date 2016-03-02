package org.itsnat.droid.impl.browser.serveritsnat.event;

import org.itsnat.droid.event.DroidFocusEvent;
import org.itsnat.droid.impl.browser.serveritsnat.evtlistener.DroidEventListener;
import org.itsnat.droid.impl.util.NameValue;

import java.util.List;

/**
 * Created by jmarranz on 7/07/14.
 */
public class DroidFocusEventImpl extends DroidEventImpl implements DroidFocusEvent
{
    protected Boolean evtNative;

    public DroidFocusEventImpl(DroidEventListener listener, Boolean evtNative)
    {
        super(listener);
        this.evtNative = evtNative;
    }

    public static Boolean createFocusEventNative(boolean hasFocus)
    {
        return new Boolean(hasFocus);
    }

    public DroidEventListener getDroidEventListener()
    {
        return (DroidEventListener)listener;
    }

    public Boolean getNativeEvent()
    {
        return evtNative;
    }

    public boolean hasFocus()
    {
        return evtNative;
    }

    @Override
    public void saveEvent()
    {
    }

    @Override
    public List<NameValue> genParamURL()
    {
        boolean hasFocus = hasFocus();

        List<NameValue> params = super.genParamURL();
        params.add(new NameValue("itsnat_evt_hasFocus",hasFocus));
        return params;
    }

}
