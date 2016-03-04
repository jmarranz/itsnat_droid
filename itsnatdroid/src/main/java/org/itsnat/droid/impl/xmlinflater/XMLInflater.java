package org.itsnat.droid.impl.xmlinflater;

import android.content.Context;

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
    protected final AttrLayoutInflaterListener attrLayoutInflaterListener;
    protected final AttrDrawableInflaterListener attrDrawableInflaterListener;

    protected XMLInflater(InflatedXML inflatedXML,int bitmapDensityReference,AttrLayoutInflaterListener attrLayoutInflaterListener,AttrDrawableInflaterListener attrDrawableInflaterListener)
    {
        this.inflatedXML = inflatedXML;
        this.bitmapDensityReference = bitmapDensityReference;
        this.attrLayoutInflaterListener = attrLayoutInflaterListener;
        this.attrDrawableInflaterListener = attrDrawableInflaterListener;
    }

    public InflatedXML getInflatedXML()
    {
        return inflatedXML;
    }

    public int getBitmapDensityReference()
    {
        return bitmapDensityReference;
    }

    public AttrLayoutInflaterListener getAttrLayoutInflaterListener()
    {
        return attrLayoutInflaterListener;
    }

    public AttrDrawableInflaterListener getAttrDrawableInflaterListener()
    {
        return attrDrawableInflaterListener;
    }

    public Context getContext()
    {
        return inflatedXML.getContext();
    }
}
