package org.itsnat.droid.impl.util;

/**
 * Created by jmarranz on 13/01/2016.
 */
public class NamespaceUtil
{
    public static final String XMLNS_ANDROID = "http://schemas.android.com/apk/res/android";

    public static String getPrefix(String name)
    {
        int pos = name.indexOf(':');
        if (pos == -1) return null;
        return name.substring(0,pos);
    }

    public static String getLocalName(String name)
    {
        int pos = name.indexOf(':');
        if (pos == -1) return name;
        return name.substring(pos + 1);
    }


}
