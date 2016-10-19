package org.itsnat.droid.impl.xmlinflated.drawable;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.drawable.XMLDOMDrawable;
import org.itsnat.droid.impl.xmlinflated.InflatedResource;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;

/**
 * Created by jmarranz on 7/11/14.
 */
public abstract class InflatedDrawable extends InflatedResource<Drawable>
{
    protected Drawable drawable;

    public InflatedDrawable(ItsNatDroidImpl itsNatDroid,XMLDOMDrawable xmlDOMDrawable,Context ctx)
    {
        super(itsNatDroid, xmlDOMDrawable,ctx);
    }

    public static InflatedDrawable createInflatedDrawable(ItsNatDroidImpl itsNatDroid,XMLDOMDrawable xmlDOMDrawable,Context ctx,PageImpl page)
    {
        return page != null ? new InflatedDrawablePage(itsNatDroid, xmlDOMDrawable, ctx,page) : new InflatedDrawableStandalone(itsNatDroid, xmlDOMDrawable, ctx);
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
