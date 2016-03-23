package org.itsnat.droid.impl.xmlinflater;

import android.content.Context;

import org.itsnat.droid.AttrAnimatorInflaterListener;
import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;

/**
 * Created by jmarranz on 4/11/14.
 */
public abstract class XMLInflater
{
    protected final InflatedXML inflatedXML;
    protected final int bitmapDensityReference;
    protected final AttrInflaterListeners attrInflaterListeners;

    protected XMLInflater(InflatedXML inflatedXML,int bitmapDensityReference,AttrInflaterListeners attrInflaterListeners)
    {
        this.inflatedXML = inflatedXML;
        this.bitmapDensityReference = bitmapDensityReference;
        this.attrInflaterListeners = attrInflaterListeners;
    }

    public InflatedXML getInflatedXML()
    {
        return inflatedXML;
    }

    public int getBitmapDensityReference()
    {
        return bitmapDensityReference;
    }

    public AttrInflaterListeners getAttrInflaterListeners()
    {
        return attrInflaterListeners;
    }

    public AttrLayoutInflaterListener getAttrLayoutInflaterListener()
    {
        return attrInflaterListeners.getAttrLayoutInflaterListener();
    }

    public AttrDrawableInflaterListener getAttrDrawableInflaterListener()
    {
        return attrInflaterListeners.getAttrDrawableInflaterListener();
    }

    public AttrAnimatorInflaterListener getAttrAnimatorInflaterListener()
    {
        return attrInflaterListeners.getAttrAnimatorInflaterListener();
    }

    public Context getContext()
    {
        return inflatedXML.getContext();
    }
}
