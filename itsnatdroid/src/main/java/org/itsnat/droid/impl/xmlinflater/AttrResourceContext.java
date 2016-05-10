package org.itsnat.droid.impl.xmlinflater;

/**
 * Created by Jose on 09/11/2015.
 */
public abstract class AttrResourceContext<TnativeResource> extends AttrContext
{
    public AttrResourceContext(XMLInflaterResource<TnativeResource> xmlInflater)
    {
        super(xmlInflater);
    }

    @SuppressWarnings("unchecked")
    public XMLInflaterResource<TnativeResource> getXMLInflaterResource()
    {
        return (XMLInflaterResource<TnativeResource>)xmlInflater;
    }
}
