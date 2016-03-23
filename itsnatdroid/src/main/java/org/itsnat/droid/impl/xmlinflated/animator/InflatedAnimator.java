package org.itsnat.droid.impl.xmlinflated.animator;

import android.animation.Animator;
import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.animator.XMLDOMAnimator;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;

/**
 * Created by jmarranz on 7/11/14.
 */
public abstract class InflatedAnimator extends InflatedXML
{
    protected Animator animator;

    public InflatedAnimator(ItsNatDroidImpl itsNatDroid, XMLDOMAnimator xmlDOMAnimator, Context ctx)
    {
        super(itsNatDroid, xmlDOMAnimator,ctx);
    }

    public static InflatedAnimator createInflatedAnimator(ItsNatDroidImpl itsNatDroid,XMLDOMAnimator xmlDOMAnimator,Context ctx,PageImpl page)
    {
        return page != null ? new InflatedAnimatorPage(itsNatDroid, xmlDOMAnimator, ctx,page) : new InflatedAnimatorStandalone(itsNatDroid, xmlDOMAnimator, ctx);
    }

    public XMLDOMAnimator getXMLDOMAnimator()
    {
        return (XMLDOMAnimator) xmlDOM;
    }

    public Animator getRootAnimator()
    {
        return animator;
    }

    public void setRootAnimator(Animator animator)
    {
        this.animator = animator;
    }

}
