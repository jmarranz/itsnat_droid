package org.itsnat.droid.impl.dom;


/**
 * Created by jmarranz on 3/11/14.
 */
public class DOMAttrCompiled extends DOMAttr
{
    public DOMAttrCompiled(String namespaceURI, String name, ResourceDescCompiled resourceDesc)
    {
        super(namespaceURI, name, resourceDesc);
    }

    public ResourceDescCompiled getResourceDescCompiled()
    {
        return (ResourceDescCompiled)resourceDesc;
    }
}
