package org.itsnat.droid.impl.dom;

/**
 * Created by jmarranz on 3/11/14.
 */
public class DOMAttrAsset extends DOMAttrLocal
{
    public DOMAttrAsset(String namespaceURI, String name, ResourceDescAsset resourceDesc)
    {
        super(namespaceURI, name, resourceDesc);
    }

    public ResourceDescAsset getResourceDescAsset()
    {
        return (ResourceDescAsset)resourceDesc;
    }
}
