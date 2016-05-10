package org.itsnat.droid.impl.xmlinflater;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.impl.xmlinflated.InflatedResource;

/**
 * Created by jmarranz on 4/11/14.
 */
public abstract class XMLInflaterResource<TnativeResource> extends XMLInflater
{
    protected XMLInflaterResource(InflatedResource<TnativeResource> inflatedResource, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        super(inflatedResource, bitmapDensityReference, attrResourceInflaterListener);
    }

    @SuppressWarnings("unchecked")
    public InflatedResource<TnativeResource> getInflatedResource()
    {
        return (InflatedResource<TnativeResource>)inflatedXML;
    }
}
