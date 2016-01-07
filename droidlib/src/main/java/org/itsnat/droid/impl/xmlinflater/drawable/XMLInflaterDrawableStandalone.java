package org.itsnat.droid.impl.xmlinflater.drawable;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.impl.xmlinflated.drawable.InflatedDrawableStandalone;

/**
 * Created by jmarranz on 24/11/14.
 */
public class XMLInflaterDrawableStandalone extends XMLInflaterDrawable
{
    public XMLInflaterDrawableStandalone(InflatedDrawableStandalone inflatedXML,int bitmapDensityReference,AttrLayoutInflaterListener attrLayoutInflaterListener,AttrDrawableInflaterListener attrDrawableInflaterListener)
    {
        super(inflatedXML,bitmapDensityReference,attrLayoutInflaterListener,attrDrawableInflaterListener);
    }
}
