package org.itsnat.droid.impl.xmlinflated.layout;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayoutStandalone;

/**
 * Created by jmarranz on 20/08/14.
 */
public class InflatedLayoutStandaloneImpl extends InflatedLayoutImpl
{
    public InflatedLayoutStandaloneImpl(ItsNatDroidImpl itsNatDroid,XMLDOMLayoutStandalone domLayout, Context ctx)
    {
        super(itsNatDroid, domLayout,ctx);
    }

}
