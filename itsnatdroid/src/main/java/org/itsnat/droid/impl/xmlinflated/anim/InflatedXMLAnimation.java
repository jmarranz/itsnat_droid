package org.itsnat.droid.impl.xmlinflated.anim;

import android.content.Context;
import android.view.animation.Animation;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.anim.XMLDOMAnimation;
import org.itsnat.droid.impl.xmlinflated.InflatedXMLResource;

/**
 * Created by jmarranz on 7/11/14.
 */
public abstract class InflatedXMLAnimation extends InflatedXMLResource<Animation>
{
    public InflatedXMLAnimation(ItsNatDroidImpl itsNatDroid, XMLDOMAnimation xmlDOMAnimation, Context ctx)
    {
        super(itsNatDroid, xmlDOMAnimation,ctx);
    }

    public static InflatedXMLAnimation createInflatedAnimation(ItsNatDroidImpl itsNatDroid, XMLDOMAnimation xmlDOMAnimation, Context ctx, PageImpl page)
    {
        return page != null ? new InflatedXMLAnimationPage(itsNatDroid, xmlDOMAnimation, ctx,page) : new InflatedXMLAnimationStandalone(itsNatDroid, xmlDOMAnimation, ctx);
    }

    public XMLDOMAnimation getXMLDOMAnimation()
    {
        return (XMLDOMAnimation) xmlDOM;
    }

}
