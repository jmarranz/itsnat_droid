package org.itsnat.droid.impl.browser;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.util.MapListReal;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Jose on 15/12/2015.
 */
public class RequestPropertyMap
{
    protected MapListReal<String,String> propertyMap = new MapListReal<String,String>();

    public RequestPropertyMap()
    {
    }

    public Map<String,List<String>> getPropertyMap() { return propertyMap.getMap(); }

    public Map<String,List<String>> getPropertyUnmodifiableMap() { return Collections.unmodifiableMap(propertyMap.getMap()); }

    public RequestPropertyMap copy()
    {
        RequestPropertyMap copy = new RequestPropertyMap();
        copy.propertyMap.getMap().putAll(this.propertyMap.getMap());
        return copy;
    }

    public String[] getProperty(String name)
    {
        List<String> values = propertyMap.get(name);
        if (values == null)
            return null;
        return values.toArray(new String[values.size()]);
    }

    public String getPropertySingle(String name)
    {
        String[] values = getProperty(name);
        if (values == null)
            return null;
        return values[values.length - 1]; // El último
    }

    public void addProperty(String name, String value)
    {
        if (value == null) throw new ItsNatDroidException("Null value is not allowed as HTTP property"); // No se si debería pero al convertir a cadena damos lugar a un "null" que no vale para nada, es preferible que el usuario use un "" que es más correcto en URLs y así hace determinista removeParameter
        propertyMap.add(name, value);
    }

    public void setProperty(String name, String value)
    {
        if (value == null) throw new ItsNatDroidException("Null value is not allowed as HTTP property"); // No se si debería pero al convertir a cadena damos lugar a un "null" que no vale para nada, es preferible que el usuario use un "" que es más correcto en URLs y así hace determinista removeParameter
        propertyMap.put(name, value);
    }

    public boolean removeProperty(String name)
    {
        return propertyMap.remove(name);
    }
}
