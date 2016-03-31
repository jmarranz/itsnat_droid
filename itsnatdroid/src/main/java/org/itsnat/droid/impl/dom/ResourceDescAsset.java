package org.itsnat.droid.impl.dom;

/**
 * Created by jmarranz on 3/11/14.
 */
public class ResourceDescAsset extends ResourceDescDynamic
{
    public ResourceDescAsset(String resourceDescValue)
    {
        super(resourceDescValue);
    }

    public static boolean isAsset(String value)
    {
        return value.startsWith("@assets:");
    }
}
