package org.itsnat.droid;

import android.content.Context;
import android.view.ViewGroup;

import java.io.InputStream;
import java.io.Reader;

/**
 * Created by jmarranz on 15/06/14.
 */
public interface InflateLayoutRequest
{
    public InflateLayoutRequest setContext(Context ctx);
    public InflateLayoutRequest setEncoding(String encoding);
    public InflateLayoutRequest setBitmapDensityReference(int density);
    public InflateLayoutRequest setAttrLayoutInflaterListener(AttrLayoutInflaterListener listener);
    public InflateLayoutRequest setAttrDrawableInflaterListener(AttrDrawableInflaterListener listener);
    public InflateLayoutRequest setAttrAnimatorInflaterListener(AttrAnimatorInflaterListener listener);
    public InflatedLayout inflate(InputStream input, ViewGroup parentView);
    public InflatedLayout inflate(Reader input, ViewGroup parentView);
}
