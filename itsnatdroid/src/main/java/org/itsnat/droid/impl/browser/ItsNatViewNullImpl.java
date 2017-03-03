package org.itsnat.droid.impl.browser;

import android.view.View;

import org.itsnat.droid.impl.browser.serveritsnat.evtlistadapter.ClickEventListenerViewAdapter;
import org.itsnat.droid.impl.browser.serveritsnat.evtlistadapter.FocusEventListenerViewAdapter;
import org.itsnat.droid.impl.browser.serveritsnat.evtlistadapter.KeyEventListenerViewAdapter;
import org.itsnat.droid.impl.browser.serveritsnat.evtlistadapter.TextChangeEventListenerViewAdapter;
import org.itsnat.droid.impl.browser.serveritsnat.evtlistadapter.TouchEventListenerViewAdapter;
import org.itsnat.droid.impl.util.MiscUtil;

/**
 * Created by jmarranz on 11/08/14.
 */
public class ItsNatViewNullImpl extends ItsNatViewImpl
{
    public ItsNatViewNullImpl(ItsNatDocPageImpl itsNatDoc)
    {
        super(itsNatDoc);
    }

    public View getView()
    {
        return null;
    }

    public String getXMLId()
    {
        return null;
    }

    @Override
    public void setXMLId(String id)
    {
        throw MiscUtil.internalError();
    }

    public ClickEventListenerViewAdapter getClickEventListenerViewAdapter()
    {
        throw MiscUtil.internalError();
    }

    public TouchEventListenerViewAdapter getTouchEventListenerViewAdapter()
    {
        throw MiscUtil.internalError();
    }

    public KeyEventListenerViewAdapter getKeyEventListenerViewAdapter()
    {
        throw MiscUtil.internalError();
    }

    public FocusEventListenerViewAdapter getFocusEventListenerViewAdapter()
    {
        throw MiscUtil.internalError();
    }

    public TextChangeEventListenerViewAdapter getTextChangeEventListenerViewAdapter()
    {
        throw MiscUtil.internalError();
    }

    public void setTextChangeEventListenerViewAdapter(TextChangeEventListenerViewAdapter textChangeEvtListenerViewAdapter)
    {
        throw MiscUtil.internalError();
    }


    public void setOnClickListener(View.OnClickListener l)
    {
        throw MiscUtil.internalError();
    }

    public void setOnTouchListener(View.OnTouchListener l)
    {
        throw MiscUtil.internalError();
    }

    public void setOnKeyListener(View.OnKeyListener l)
    {
        throw MiscUtil.internalError();
    }

    public void setOnFocusChangeListener(View.OnFocusChangeListener l)
    {
        throw MiscUtil.internalError();
    }

}
