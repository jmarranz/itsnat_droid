package org.itsnat.droid.impl.dom;


/**
 * Created by jmarranz on 3/11/14.
 */
public class ResourceDescRemote extends ResourceDescDynamic
{
    public ResourceDescRemote(String resourceDescValue)
    {
        super(resourceDescValue);
    }

    public boolean isDownloaded()
    {
        return parsedResource != null;
    }

    public static boolean isRemote(String value)
    {
        return value.startsWith("@remote:");
    }
}
