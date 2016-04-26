package org.itsnat.droid.impl.xmlinflated.anim;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.anim.XMLDOMAnimation;

/**
 * Created by jmarranz on 20/08/14.
 */
public class InflatedAnimationStandalone extends InflatedAnimation
{
    public InflatedAnimationStandalone(ItsNatDroidImpl itsNatDroid, XMLDOMAnimation xmlDOMAnimation, Context ctx)
    {
        super(itsNatDroid, xmlDOMAnimation,ctx);
    }
}
