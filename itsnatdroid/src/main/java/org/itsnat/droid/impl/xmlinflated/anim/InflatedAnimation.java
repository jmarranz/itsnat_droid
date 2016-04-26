package org.itsnat.droid.impl.xmlinflated.anim;

import android.content.Context;
import android.view.animation.Animation;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.anim.XMLDOMAnimation;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;

/**
 * Created by jmarranz on 7/11/14.
 */
public abstract class InflatedAnimation extends InflatedXML
{
    protected Animation animation;

    public InflatedAnimation(ItsNatDroidImpl itsNatDroid, XMLDOMAnimation xmlDOMAnimation, Context ctx)
    {
        super(itsNatDroid, xmlDOMAnimation,ctx);
    }

    public static InflatedAnimation createInflatedAnimation(ItsNatDroidImpl itsNatDroid, XMLDOMAnimation xmlDOMAnimation, Context ctx, PageImpl page)
    {
        return page != null ? new InflatedAnimationPage(itsNatDroid, xmlDOMAnimation, ctx,page) : new InflatedAnimationStandalone(itsNatDroid, xmlDOMAnimation, ctx);
    }

    public XMLDOMAnimation getXMLDOMAnimation()
    {
        return (XMLDOMAnimation) xmlDOM;
    }

    public Animation getRootAnimation()
    {
        return animation;
    }

    public void setRootAnimation(Animation animation)
    {
        this.animation = animation;
    }

}
