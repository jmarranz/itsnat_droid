package org.itsnat.droid.impl.xmlinflater;

import android.content.Context;

import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;


/**
 * Created by jmarranz on 12/04/2016.
 */
public class XMLInflaterContext
{
    protected final ItsNatDroidImpl itsNatDroid;
    protected final Context ctx;
    protected final PageImpl page; // PUEDE SER NULL
    protected final int bitmapDensityReference;
    protected final AttrInflaterListeners attrInflaterListeners;

    public XMLInflaterContext(ItsNatDroidImpl itsNatDroid,Context ctx, PageImpl page, int bitmapDensityReference, AttrInflaterListeners attrInflaterListeners)
    {
        this.itsNatDroid = itsNatDroid;
        this.ctx = ctx;
        this.page = page;
        this.bitmapDensityReference = bitmapDensityReference;
        this.attrInflaterListeners = attrInflaterListeners;
    }

    public ItsNatDroidImpl getItsNatDroidImpl()
    {
        return itsNatDroid;
    }

    public Context getContext()
    {
        return ctx;
    }

    public PageImpl getPageImpl()
    {
        return page;
    }

    public int getBitmapDensityReference()
    {
        return bitmapDensityReference;
    }

    public AttrInflaterListeners getAttrInflaterListeners()
    {
        return attrInflaterListeners;
    }

    public XMLInflaterRegistry getXMLInflaterRegistry()
    {
        return itsNatDroid.getXMLInflaterRegistry();
    }
}
