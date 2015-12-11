package org.itsnat.droid.impl.xmlinflater.layout.page;

import android.content.Context;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.ItsNatDroidBrowserImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.browser.PageRequestImpl;
import org.itsnat.droid.impl.xmlinflater.layout.InflateLayoutRequestImpl;

/**
 * Created by jmarranz on 5/06/14.
 */
public class InflateLayoutRequestPageImpl extends InflateLayoutRequestImpl
{
    protected PageImpl page;

    public InflateLayoutRequestPageImpl(ItsNatDroidImpl itsNatDroid, PageImpl page)
    {
        super(itsNatDroid);
        this.page = page; // NO puede ser nulo
    }

    /*
    public static ItsNatDroidImpl getItsNatDroidImpl(PageImpl page)
    {
        PageRequestImpl pageRequest = page.getPageRequestClonedImpl();
        ItsNatDroidBrowserImpl browser = pageRequest.getItsNatDroidBrowserImpl();
        ItsNatDroidImpl itsNatDroid = browser.getItsNatDroidImpl();
        return itsNatDroid;
    }
*/

    public String getEncoding()
    {
        return page.getHttpRequestResultOKImpl().getEncoding();
    }

    public int getBitmapDensityReference()
    {
        return page.getBitmapDensityReference();
    }

    public AttrLayoutInflaterListener getAttrLayoutInflaterListener()
    {
        return page.getPageRequestClonedImpl().getAttrLayoutInflaterListener();
    }

    public AttrDrawableInflaterListener getAttrDrawableInflaterListener()
    {
        return page.getPageRequestClonedImpl().getAttrDrawableInflaterListener();
    }

    public Context getContext()
    {
        return page.getPageRequestClonedImpl().getContext();
    }

}
