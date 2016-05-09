package org.itsnat.droid.impl.xmlinflater.layout;

import android.content.Context;
import android.view.ViewGroup;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.impl.ItsNatDroidImpl;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;

/**
 * Created by jmarranz on 5/06/14.
 */
public abstract class InflateLayoutRequestImpl
{
    protected ItsNatDroidImpl itsNatDroid;

    public InflateLayoutRequestImpl(ItsNatDroidImpl itsNatDroid)
    {
        this.itsNatDroid = itsNatDroid;
    }

    public ItsNatDroidImpl getItsNatDroidImpl()
    {
        return itsNatDroid;
    }

    public abstract int getBitmapDensityReference();

    public abstract String getEncoding();

    public abstract AttrResourceInflaterListener getAttrResourceInflaterListener();

    public abstract Context getContext();

    public XMLInflaterLayout inflateLayout(XMLDOMLayout xmlDOMLayout,ViewGroup parentView,int indexChild,PageImpl page)
    {
        return XMLInflaterLayout.inflateLayout(itsNatDroid,xmlDOMLayout,parentView,indexChild,getBitmapDensityReference(),getAttrResourceInflaterListener(),getContext(),page);
    }

}
