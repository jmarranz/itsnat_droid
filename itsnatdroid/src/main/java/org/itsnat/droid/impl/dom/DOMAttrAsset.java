package org.itsnat.droid.impl.dom;

/**
 * Created by jmarranz on 3/11/14.
 */
public class DOMAttrAsset extends DOMAttrDynamic
{
    public DOMAttrAsset(String namespaceURI, String name, String value)
    {
        super(namespaceURI, name, value);
    }

    public static boolean isAsset(String value)
    {
        return value.startsWith("@assets:");
    }
}
