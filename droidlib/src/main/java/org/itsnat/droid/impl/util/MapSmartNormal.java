package org.itsnat.droid.impl.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Jose on 09/11/2015.
 */
public class MapSmartNormal<Key,Value> extends MapSmart<Key,Value>
{
    protected Map<Key,Value> map = new HashMap<Key,Value>();

    public MapSmartNormal()
    {
    }

    @Override
    public Iterator<Map.Entry<Key,Value>> iterator()
    {
        return map.entrySet().iterator();
    }

    @Override
    public Value get(Key o)
    {
        return map.get(o);
    }

    @Override
    public void put(Key o, Value o2)
    {
        map.put(o,o2);
    }

    @Override
    public boolean remove(Key o)
    {
        Value res = map.remove(o);
        return res != null;
    }
}
