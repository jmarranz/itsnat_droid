package org.itsnat.droid.impl.xmlinflater.drawable;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.xmlinflater.AttrResourceContext;

/**
 * Created by Jose on 09/11/2015.
 */
public class AttrDrawableContext extends AttrResourceContext<Drawable>
{
    public AttrDrawableContext(XMLInflaterDrawable xmlInflaterDrawable)
    {
        super(xmlInflaterDrawable);
    }

    public XMLInflaterDrawable getXMLInflaterDrawable()
    {
        return (XMLInflaterDrawable)xmlInflater;
    }
}
