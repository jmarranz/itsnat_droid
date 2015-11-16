package org.itsnat.droid.impl.xmlinflater.drawable;

import android.content.Context;

import org.itsnat.droid.impl.xmlinflater.AttrContext;

/**
 * Created by Jose on 09/11/2015.
 */
public class AttrDrawableContext extends AttrContext
{
    public AttrDrawableContext(Context ctx, XMLInflaterDrawable xmlInflaterDrawable)
    {
        super(ctx,xmlInflaterDrawable);
    }

    public XMLInflaterDrawable getXMLInflaterDrawable()
    {
        return (XMLInflaterDrawable)xmlInflater;
    }
}
