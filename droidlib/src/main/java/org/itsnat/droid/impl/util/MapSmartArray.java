package org.itsnat.droid.impl.util;

/**
 * Created by Jose on 09/11/2015.
 */
public class MapSmartArray<Key,Value> extends MapSmart<Key,Value>
{
    protected MapLight<Key,Value> map = new MapLight<Key,Value>();

    public MapSmartArray(int items)
    {
        this.map = new MapLight<Key,Value>(items);
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
