package org.itsnat.droid.impl.dom;

import java.util.Locale;

/**
 * Created by jmarranz on 3/11/14.
 */
public class DOMAttrAsset extends DOMAttrDynamic
{
    public DOMAttrAsset(String namespaceURI, String name, String value,Locale locale)
    {
        super(namespaceURI, name, value,locale);
    }

    public static boolean isAsset(String value)
    {
        return value.startsWith("@assets:");
    }
}
