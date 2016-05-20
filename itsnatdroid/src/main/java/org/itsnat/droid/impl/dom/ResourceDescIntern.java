package org.itsnat.droid.impl.dom;

/**
 * Created by jmarranz on 3/11/14.
 */
public class ResourceDescIntern extends ResourceDescLocal
{
    public ResourceDescIntern(String resourceDescValue)
    {
        super(resourceDescValue);
    }

    public static boolean isIntern(String value)
    {
        return value.startsWith("@intern:");
    }
}
