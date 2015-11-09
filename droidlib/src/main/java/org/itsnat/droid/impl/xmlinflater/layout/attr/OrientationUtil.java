package org.itsnat.droid.impl.xmlinflater.layout.attr;

import org.itsnat.droid.impl.util.MapSmart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmarranz on 28/07/14.
 */
public class OrientationUtil
{
    public static final MapSmart<String,Integer> valueMap = MapSmart.<String,Integer>create(2);
    static
    {
        valueMap.put("horizontal", 0);
        valueMap.put("vertical", 1);
    }
}
