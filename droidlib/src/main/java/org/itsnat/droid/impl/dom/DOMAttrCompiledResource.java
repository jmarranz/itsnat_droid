package org.itsnat.droid.impl.dom;

/**
 * Created by jmarranz on 3/11/14.
 */
public class DOMAttrCompiledResource extends DOMAttr
{
    public DOMAttrCompiledResource(String namespaceURI, String name, String value)
    {
        super(namespaceURI, name, value);
    }

    public static DOMAttrCompiledResource createDOMAttrCompiledResource(DOMAttrCompiledResource attr, String newValue)
    {
        return new DOMAttrCompiledResource(attr.getNamespaceURI(),attr.getName(),newValue);
    }
}
