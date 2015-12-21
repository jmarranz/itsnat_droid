package org.itsnat.droid.impl.browser.serveritsnat.event;

import org.itsnat.droid.event.Event;
import org.itsnat.droid.impl.browser.serveritsnat.evtlistener.EventGenericListener;
import org.itsnat.droid.impl.util.MapLight;
import org.itsnat.droid.impl.util.NameValue;

import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 7/07/14.
 */
public abstract class EventGenericImpl implements Event
{
    protected EventGenericListener listener;
    protected boolean mustBeSent = true;
    protected MapLight<String,Object> extraParams;

    public EventGenericImpl(EventGenericListener listener)
    {
        this.listener = listener;
    }

    public void setMustBeSent(boolean value) { this.mustBeSent = value; }
    public void sendEvent()
    {
        if (this.mustBeSent) listener.getItsNatDocImpl().getEventManager().sendEvent(this);
    }

    public boolean isIgnoreHold()
    {
        return false;
    }

    public String getType()
    {
        return null; // Se redefine
    }

    public void onEventReturned()
    {
        // Redefinir en derivada si se quiere hacer algo
    }

    public abstract void saveEvent();

    public EventGenericListener getEventGenericListener()
    {
        return listener;
    }

    public Object getExtraParam(String name)
    {
        if (extraParams == null) return null;
        return extraParams.get(name);
    }

    public void setExtraParam(String name,Object value)
    {
        if (extraParams == null) this.extraParams = new MapLight<String,Object>();
        extraParams.put(name,value);
    }

    public List<NameValue> genParamURL()
    {
        List<NameValue> paramList = listener.getItsNatDocImpl().genParamURL();
        listener.genParamURL(this,paramList);

        if (extraParams != null)
        {
            for (Map.Entry<String,Object> entry : extraParams.getEntryList())
            {
                String name = entry.getKey();
                Object value = entry.getValue();
                if (value != null && value instanceof Object[]) // Aunque sea String[] es válido el instanceof pues Object[] es la "clase base"
                {
                    Object[] valueArr = (Object[])value;
                    for (int i = 0; i < valueArr.length; i++)
                    {
                        if (valueArr[i] == null) continue;
                        paramList.add(new NameValue(name, valueArr[i]));
                    }
                }
                else
                {
                    if (value != null)
                        paramList.add(new NameValue(name,value));
                }
            }
        }

        return paramList;
    }

}
