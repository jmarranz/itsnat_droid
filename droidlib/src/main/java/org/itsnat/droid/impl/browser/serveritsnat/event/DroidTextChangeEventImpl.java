package org.itsnat.droid.impl.browser.serveritsnat.event;

import org.itsnat.droid.event.DroidTextChangeEvent;
import org.itsnat.droid.impl.browser.serveritsnat.evtlistener.DroidEventListener;
import org.itsnat.droid.impl.util.NameValue;

import java.util.List;

/**
 * Created by jmarranz on 7/07/14.
 */
public class DroidTextChangeEventImpl extends DroidEventImpl implements DroidTextChangeEvent
{
    protected CharSequence evtNative;

    public DroidTextChangeEventImpl(DroidEventListener listener, CharSequence evtNative)
    {
        super(listener);
        this.evtNative = evtNative;
    }

    public static CharSequence createTextChangeEventNative(CharSequence newText)
    {
        return newText;
    }

    public DroidEventListener getDroidEventListener()
    {
        return (DroidEventListener)listener;
    }

    public CharSequence getNativeEvent()
    {
        return evtNative;
    }

    public CharSequence getNewText()
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
        CharSequence newText = getNewText();

        List<NameValue> params = super.genParamURL();
        params.add(new NameValue("itsnat_evt_newText",newText.toString()));
        return params;
    }

}
