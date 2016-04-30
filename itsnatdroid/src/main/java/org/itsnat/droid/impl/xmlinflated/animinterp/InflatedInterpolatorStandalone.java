package org.itsnat.droid.impl.xmlinflated.animinterp;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.animinterp.XMLDOMInterpolator;

/**
 * Created by jmarranz on 20/08/14.
 */
public class InflatedInterpolatorStandalone extends InflatedInterpolator
{
    public InflatedInterpolatorStandalone(ItsNatDroidImpl itsNatDroid, XMLDOMInterpolator xmlDOMInterpolator, Context ctx)
    {
        super(itsNatDroid, xmlDOMInterpolator,ctx);
    }
}
