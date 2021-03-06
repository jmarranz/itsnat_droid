package org.itsnat.droid.impl.xmlinflater;

import android.content.Context;

import org.itsnat.droid.AttrResourceInflaterListener;
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
    protected final AttrResourceInflaterListener attrResourceInflaterListener;

    public XMLInflaterContext(ItsNatDroidImpl itsNatDroid,Context ctx, PageImpl page, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        this.itsNatDroid = itsNatDroid;
        this.ctx = ctx;
        this.page = page;
        this.bitmapDensityReference = bitmapDensityReference;
        this.attrResourceInflaterListener = attrResourceInflaterListener;
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

    public AttrResourceInflaterListener getAttrResourceInflaterListener()
    {
        return attrResourceInflaterListener;
    }

    public XMLInflaterRegistry getXMLInflaterRegistry()
    {
        return itsNatDroid.getXMLInflaterRegistry();
    }
}
