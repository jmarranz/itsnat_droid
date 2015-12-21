package org.itsnat.droid.impl.browser.serveritsnat.evtlistener;

import android.view.View;

import org.itsnat.droid.impl.browser.serveritsnat.CustomFunction;
import org.itsnat.droid.impl.browser.serveritsnat.DroidEventGroupInfo;
import org.itsnat.droid.impl.browser.serveritsnat.ItsNatDocImpl;
import org.itsnat.droid.impl.browser.serveritsnat.event.EventGenericImpl;
import org.itsnat.droid.impl.browser.serveritsnat.event.NormalEventImpl;
import org.itsnat.droid.impl.util.NameValue;

import java.util.List;

/**
 * Created by jmarranz on 4/07/14.
 */
public class DroidEventListener extends NormalEventListener
{
    protected String type;
    protected boolean useCapture;
    protected int eventGroupCode;

    public DroidEventListener(ItsNatDocImpl parent, View currentTarget, String type, CustomFunction customFunc, String id, boolean useCapture, int commMode, long timeout, int eventGroupCode)
    {
        super(parent,"droid",currentTarget,customFunc,id,commMode,timeout);
        this.type = type;
        this.useCapture = useCapture;
        this.eventGroupCode = eventGroupCode;
    }

    public boolean isUseCapture()
    {
        return useCapture;
    }

    public String getType()
    {
        return type;
    }

    @Override
    public NormalEventImpl createNormalEvent(Object evt)
    {
        return DroidEventGroupInfo.createDroidEvent(eventGroupCode, this, evt);
    }

    @Override
    public void genParamURL(EventGenericImpl evt,List<NameValue> paramList)
    {
        super.genParamURL(evt, paramList);
        paramList.add(new NameValue("itsnat_evt_type", this.type));
    }
}
