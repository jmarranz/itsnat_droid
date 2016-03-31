package org.itsnat.droid.impl.dom;

/**
 * Created by jmarranz on 3/11/14.
 */
public abstract class DOMAttrDynamic extends DOMAttr
{
    public DOMAttrDynamic(String namespaceURI, String name, ResourceDescDynamic resourceDesc)
    {
        super(namespaceURI, name, resourceDesc);
    }

    public ResourceDescDynamic getResourceDescDynamic()
    {
        return (ResourceDescDynamic)resourceDesc;
    }
}
