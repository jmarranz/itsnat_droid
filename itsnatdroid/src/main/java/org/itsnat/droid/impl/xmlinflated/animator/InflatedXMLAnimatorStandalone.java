package org.itsnat.droid.impl.xmlinflated.animator;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.dom.animator.XMLDOMAnimator;

/**
 * Created by jmarranz on 20/08/14.
 */
public class InflatedXMLAnimatorStandalone extends InflatedXMLAnimator
{
    public InflatedXMLAnimatorStandalone(ItsNatDroidImpl itsNatDroid, XMLDOMAnimator xmlDOMAnimator, Context ctx)
    {
        super(itsNatDroid, xmlDOMAnimator,ctx);
    }
}
