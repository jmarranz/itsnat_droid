package org.itsnat.droid.impl.xmlinflated.animlayout;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.animlayout.XMLDOMLayoutAnimation;

/**
 * Created by jmarranz on 20/08/14.
 */
public class InflatedXMLLayoutAnimationStandalone extends InflatedXMLLayoutAnimation
{
    public InflatedXMLLayoutAnimationStandalone(ItsNatDroidImpl itsNatDroid, XMLDOMLayoutAnimation xmlDOMLayoutAnimation, Context ctx)
    {
        super(itsNatDroid, xmlDOMLayoutAnimation,ctx);
    }
}
