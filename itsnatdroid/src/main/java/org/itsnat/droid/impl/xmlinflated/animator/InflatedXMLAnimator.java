package org.itsnat.droid.impl.xmlinflated.animator;

import android.animation.Animator;
import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.animator.XMLDOMAnimator;
import org.itsnat.droid.impl.xmlinflated.InflatedXMLResource;

/**
 * Created by jmarranz on 7/11/14.
 */
public abstract class InflatedXMLAnimator extends InflatedXMLResource<Animator>
{
    public InflatedXMLAnimator(ItsNatDroidImpl itsNatDroid, XMLDOMAnimator xmlDOMAnimator, Context ctx)
    {
        super(itsNatDroid, xmlDOMAnimator,ctx);
    }

    public static InflatedXMLAnimator createInflatedXMLAnimator(ItsNatDroidImpl itsNatDroid, XMLDOMAnimator xmlDOMAnimator, Context ctx, PageImpl page)
    {
        return page != null ? new InflatedXMLAnimatorPage(itsNatDroid, xmlDOMAnimator, ctx,page) : new InflatedXMLAnimatorStandalone(itsNatDroid, xmlDOMAnimator, ctx);
    }

    public XMLDOMAnimator getXMLDOMAnimator()
    {
        return (XMLDOMAnimator) xmlDOM;
    }

}
