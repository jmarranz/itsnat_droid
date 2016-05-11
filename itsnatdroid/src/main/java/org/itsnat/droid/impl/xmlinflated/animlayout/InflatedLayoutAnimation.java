package org.itsnat.droid.impl.xmlinflated.animlayout;

import android.content.Context;
import android.view.animation.LayoutAnimationController;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.animlayout.XMLDOMLayoutAnimation;
import org.itsnat.droid.impl.xmlinflated.InflatedResource;

/**
 * Created by jmarranz on 7/11/14.
 */
public abstract class InflatedLayoutAnimation extends InflatedResource<LayoutAnimationController>
{
    public InflatedLayoutAnimation(ItsNatDroidImpl itsNatDroid, XMLDOMLayoutAnimation xmlDOMAnimation, Context ctx)
    {
        super(itsNatDroid, xmlDOMAnimation,ctx);
    }

    public static InflatedLayoutAnimation createInflatedLayoutAnimation(ItsNatDroidImpl itsNatDroid, XMLDOMLayoutAnimation xmlDOMLayoutAnimation, Context ctx, PageImpl page)
    {
        return page != null ? new InflatedLayoutAnimationPage(itsNatDroid, xmlDOMLayoutAnimation, ctx,page) : new InflatedLayoutAnimationStandalone(itsNatDroid, xmlDOMLayoutAnimation, ctx);
    }

    public XMLDOMLayoutAnimation getXMLDOMLayoutAnimation()
    {
        return (XMLDOMLayoutAnimation) xmlDOM;
    }

}
