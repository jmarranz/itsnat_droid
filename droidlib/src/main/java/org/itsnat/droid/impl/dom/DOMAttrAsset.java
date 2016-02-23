package org.itsnat.droid.impl.dom;

import android.content.res.Configuration;

/**
 * Created by jmarranz on 3/11/14.
 */
public class DOMAttrAsset extends DOMAttrDynamic
{
    public DOMAttrAsset(String namespaceURI, String name, String value,Configuration configuration)
    {
        super(namespaceURI, name, value, configuration);
    }

    public static boolean isAsset(String value)
    {
        return value.startsWith("@assets:");
    }
}
