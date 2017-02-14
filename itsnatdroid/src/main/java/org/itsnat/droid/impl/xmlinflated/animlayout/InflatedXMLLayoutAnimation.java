package org.itsnat.droid.impl.xmlinflated.animlayout;

import android.content.Context;
import android.view.animation.LayoutAnimationController;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.animlayout.XMLDOMLayoutAnimation;
import org.itsnat.droid.impl.xmlinflated.InflatedXMLResource;

/**
 * Created by jmarranz on 7/11/14.
 */
public abstract class InflatedXMLLayoutAnimation extends InflatedXMLResource<LayoutAnimationController>
{
    public InflatedXMLLayoutAnimation(ItsNatDroidImpl itsNatDroid, XMLDOMLayoutAnimation xmlDOMAnimation, Context ctx)
    {
        super(itsNatDroid, xmlDOMAnimation,ctx);
    }

    public static InflatedXMLLayoutAnimation createInflatedLayoutAnimation(ItsNatDroidImpl itsNatDroid, XMLDOMLayoutAnimation xmlDOMLayoutAnimation, Context ctx, PageImpl page)
    {
        return page != null ? new InflatedXMLLayoutAnimationPage(itsNatDroid, xmlDOMLayoutAnimation, ctx,page) : new InflatedXMLLayoutAnimationStandalone(itsNatDroid, xmlDOMLayoutAnimation, ctx);
    }

    public XMLDOMLayoutAnimation getXMLDOMLayoutAnimation()
    {
        return (XMLDOMLayoutAnimation) xmlDOM;
    }

}
