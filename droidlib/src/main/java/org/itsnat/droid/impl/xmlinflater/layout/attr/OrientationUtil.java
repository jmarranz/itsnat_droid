package org.itsnat.droid.impl.xmlinflater.layout.attr;

import org.itsnat.droid.impl.util.MapSmart;

/**
 * Created by jmarranz on 28/07/14.
 */
public class OrientationUtil
{
    public static final MapSmart<String,Integer> nameValueMap = MapSmart.<String,Integer>create(2);
    static
    {
        nameValueMap.put("horizontal", 0);
        nameValueMap.put("vertical", 1);
    }
}
