package org.itsnat.droid.impl.xmlinflated.animinterp;

import android.content.Context;
import android.view.animation.Interpolator;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.animinterp.XMLDOMInterpolator;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;

/**
 * Created by jmarranz on 7/11/14.
 */
public abstract class InflatedInterpolator extends InflatedXML
{
    protected Interpolator interpolator;

    public InflatedInterpolator(ItsNatDroidImpl itsNatDroid, XMLDOMInterpolator xmlDOMInterpolator, Context ctx)
    {
        super(itsNatDroid, xmlDOMInterpolator,ctx);
    }

    public static InflatedInterpolator createInflatedInterpolator(ItsNatDroidImpl itsNatDroid, XMLDOMInterpolator xmlDOMInterpolator, Context ctx, PageImpl page)
    {
        return page != null ? new InflatedInterpolatorPage(itsNatDroid, xmlDOMInterpolator, ctx,page) : new InflatedInterpolatorStandalone(itsNatDroid, xmlDOMInterpolator, ctx);
    }

    public XMLDOMInterpolator getXMLDOMInterpolator()
    {
        return (XMLDOMInterpolator) xmlDOM;
    }

    public Interpolator getRootInterpolator()
    {
        return interpolator;
    }

    public void setRootInterpolator(Interpolator interpolator)
    {
        this.interpolator = interpolator;
    }

}
