package org.itsnat.droid.impl.util;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Jose on 09/11/2015.
 */
public abstract class MapSmart<Key,Value>
{
    public static <Key,Value> MapSmart<Key,Value> create(int items)
    {
        if (items >= 8) return new MapSmartNormal<Key,Value>(); // Compensa hacer como máximo 3 búsquedas en vez de ocho
        else return new MapSmartArray<Key,Value>(items);
    }

    public abstract Iterator<Map.Entry<Key,Value>> iterator();

    public abstract Value get(Key key);

    public abstract void put(Key key, Value value);

    public abstract boolean remove(Key key);
}
