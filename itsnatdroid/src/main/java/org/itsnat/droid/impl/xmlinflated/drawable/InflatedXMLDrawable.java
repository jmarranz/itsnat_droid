package org.itsnat.droid.impl.xmlinflated.drawable;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.drawable.XMLDOMDrawable;
import org.itsnat.droid.impl.xmlinflated.InflatedXMLResource;

/**
 * Created by jmarranz on 7/11/14.
 */
public abstract class InflatedXMLDrawable extends InflatedXMLResource<Drawable>
{
    protected Drawable drawable;

    public InflatedXMLDrawable(ItsNatDroidImpl itsNatDroid, XMLDOMDrawable xmlDOMDrawable, Context ctx)
    {
        super(itsNatDroid, xmlDOMDrawable,ctx);
    }

    public static InflatedXMLDrawable createInflatedXMLDrawable(ItsNatDroidImpl itsNatDroid, XMLDOMDrawable xmlDOMDrawable, Context ctx, PageImpl page)
    {
        return page != null ? new InflatedXMLDrawablePage(itsNatDroid, xmlDOMDrawable, ctx,page) : new InflatedXMLDrawableStandalone(itsNatDroid, xmlDOMDrawable, ctx);
    }

    public XMLDOMDrawable getXMLDOMDrawable()
    {
        return (XMLDOMDrawable) xmlDOM;
    }

    public Drawable getDrawable()
    {
        return drawable;
    }

    public void setDrawable(Drawable drawable)
    {
        this.drawable = drawable;
    }

}
