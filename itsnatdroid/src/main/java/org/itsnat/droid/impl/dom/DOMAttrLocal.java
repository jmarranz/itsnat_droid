package org.itsnat.droid.impl.dom;

/**
 * Created by jmarranz on 3/11/14.
 */
public abstract class DOMAttrLocal extends DOMAttrDynamic
{
    public DOMAttrLocal(String namespaceURI, String name, ResourceDescLocal resourceDesc)
    {
        super(namespaceURI, name, resourceDesc);
    }

    public ResourceDescLocal getResourceDescLocal()
    {
        return (ResourceDescLocal)resourceDesc;
    }
}
