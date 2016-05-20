package org.itsnat.droid.impl.dom;

/**
 * Created by jmarranz on 3/11/14.
 */
public class DOMAttrIntern extends DOMAttrLocal
{
    public DOMAttrIntern(String namespaceURI, String name, ResourceDescIntern resourceDesc)
    {
        super(namespaceURI, name, resourceDesc);
    }

    public ResourceDescIntern getResourceDescIntern()
    {
        return (ResourceDescIntern)resourceDesc;
    }
}
