package org.itsnat.droid.impl.util;

import org.itsnat.droid.ItsNatDroidException;

/**
 * Created by jmarranz on 13/01/2016.
 */
public class NamespaceUtil
{
    public static final String XMLNS_ANDROID = "http://schemas.android.com/apk/res/android";
    public static final String XMLNS_ANDROID_ALIAS = "NSAND"; // Versión corta para reducir código que se envía desde el servidor ItsNat
    public static final String XMLNS_ITSNATDROID_RESOURCE = "http://itsnat.org/itsnatdroid/resource";

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

    public static ItsNatDroidException resourceStillNotLoadedException(String resourceDescValue)
    {
        String msg = "" +
                "Resource \"" + resourceDescValue + "\" is still not loaded, if it is a remote resource, maybe you should declare this resource as an attribute with namespace " + XMLNS_ITSNATDROID_RESOURCE + " in the View element root for manual load declaration. " +
                "For instance, when the resource \"@remote:animator/droid/res/animator/test_value_animator_remote.xml\" is not found add: " +
                "<ScrollView xmlns:android=\"http://schemas.android.com/apk/res/android\" " +
                "    xmlns:indres=\"http://itsnat.org/itsnatdroid/resource\" " +
                "    indres:valueAnimatorTest=\"@remote:animator/droid/res/animator/test_value_animator_remote.xml\" " +
                "..." +
                "   \"indres\" and \"valueAnimatorTest\" are not mandatory";
        return new ItsNatDroidException(msg);
    }
}
