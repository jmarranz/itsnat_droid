package org.itsnat.droid.impl.xmlinflater;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.impl.xmlinflated.InflatedXMLResource;

/**
 * Created by jmarranz on 4/11/14.
 */
public abstract class XMLInflaterResource<TnativeResource> extends XMLInflater
{
    protected XMLInflaterResource(InflatedXMLResource<TnativeResource> inflatedXMLResource, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        super(inflatedXMLResource, bitmapDensityReference, attrResourceInflaterListener);
    }

    @SuppressWarnings("unchecked")
    public InflatedXMLResource<TnativeResource> getInflatedXMLResource()
    {
        return (InflatedXMLResource<TnativeResource>)inflatedXML;
    }
}
