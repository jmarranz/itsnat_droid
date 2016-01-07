package org.itsnat.droid.impl.xmlinflater.values;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.impl.xmlinflated.values.InflatedValuesStandalone;


/**
 * Created by jmarranz on 24/11/14.
 */
public class XMLInflaterValuesStandalone extends XMLInflaterValues
{
    public XMLInflaterValuesStandalone(InflatedValuesStandalone inflatedXML, int bitmapDensityReference, AttrLayoutInflaterListener attrLayoutInflaterListener, AttrDrawableInflaterListener attrDrawableInflaterListener)
    {
        super(inflatedXML,bitmapDensityReference,attrLayoutInflaterListener,attrDrawableInflaterListener);
    }
}
