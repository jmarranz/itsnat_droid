package org.itsnat.droid.impl.xmlinflated.animinterp;

import android.content.Context;
import android.view.animation.Interpolator;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.animinterp.XMLDOMInterpolator;
import org.itsnat.droid.impl.xmlinflated.InflatedXMLResource;

/**
 * Created by jmarranz on 7/11/14.
 */
public abstract class InflatedXMLInterpolator extends InflatedXMLResource<Interpolator>
{
    public InflatedXMLInterpolator(ItsNatDroidImpl itsNatDroid, XMLDOMInterpolator xmlDOMInterpolator, Context ctx)
    {
        super(itsNatDroid, xmlDOMInterpolator,ctx);
    }

    public static InflatedXMLInterpolator createInflatedInterpolator(ItsNatDroidImpl itsNatDroid, XMLDOMInterpolator xmlDOMInterpolator, Context ctx, PageImpl page)
    {
        return page != null ? new InflatedXMLInterpolatorPage(itsNatDroid, xmlDOMInterpolator, ctx,page) : new InflatedXMLInterpolatorStandalone(itsNatDroid, xmlDOMInterpolator, ctx);
    }

    public XMLDOMInterpolator getXMLDOMInterpolator()
    {
        return (XMLDOMInterpolator) xmlDOM;
    }

}
