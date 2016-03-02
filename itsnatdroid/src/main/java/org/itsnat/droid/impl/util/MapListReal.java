package org.itsnat.droid.impl.util;

import org.itsnat.droid.ItsNatDroidException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 4/07/14.
 */
public class MapListReal<Key,Value> implements MapList<Key,Value>
{
    protected Map<Key,List<Value>> map = new HashMap<Key,List<Value>>();

    public MapListReal()
    {
    }

    public Map<Key,List<Value>> getMap()
    {
        return map;
    }

    public List<Value> get(Key key)
    {
        return map.get(key);
    }

    public void add(Key key,Value value)
    {
        if (value == null) throw new ItsNatDroidException("Null value is not allowed");
        List<Value> list = map.get(key);
        if (list == null)
        {
            list = new LinkedList<Value>();
            map.put(key,list);
        }
        list.add(value);
    }

    public void put(Key key,Value value)
    {
        if (value == null) throw new ItsNatDroidException("Null value is not allowed");

        remove(key);
        add(key,value);
    }

    public boolean remove(Key key)
    {
        // Si es null es que no existe porque no permitimos null, cuando no hay valores se devuelve null porque no permitimos una la lista nula
        return map.remove(key) != null;
    }

    public boolean remove(Key key,Value value)
    {
        List<Value> valueList = map.get(key);
        if (valueList == null) return false;
        for(Iterator<Value> it = valueList.iterator(); it.hasNext(); )
        {
            Value currValue = it.next();
            if (MiscUtil.equalsNullAllowed(value, currValue)) // currValue nunca va a ser nulo pero lo dejamos por si cambiamos de opinión y permitimos nulos y delegamos la decisión a una capa superior
            {
                it.remove();
                if (valueList.isEmpty()) map.remove(key);
                return true;
            }
        }
        return false;
    }
}
