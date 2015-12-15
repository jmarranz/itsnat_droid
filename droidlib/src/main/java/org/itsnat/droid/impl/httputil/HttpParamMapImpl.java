package org.itsnat.droid.impl.httputil;

import org.itsnat.droid.HttpParamMap;
import org.itsnat.droid.ItsNatDroidException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jose on 15/12/2015.
 */
public class HttpParamMapImpl implements HttpParamMap
{
    protected Map<String,Object> paramMap = new HashMap<String,Object>();

    public HttpParamMapImpl()
    {
    }

    public Map<String,Object> getParamMap() { return paramMap; }

    public HttpParamMapImpl copy()
    {
        HttpParamMapImpl copy = new HttpParamMapImpl();
        copy.paramMap.putAll(this.paramMap);
        return copy;
    }

    @Override
    public Object getParameter(String name)
    {
        return paramMap.get(name);
    }

    @Override
    public HttpParamMap setParameter(String name, Object value)
    {
        if (value == null) throw new ItsNatDroidException("Null value is not allowed as HTTP parameter"); // No se si debería pero al convertir a cadena damos lugar a un "null" que no vale para nada, es preferible que el usuario use un "" que es más correcto en URLs y así hace determinista removeParameter
        paramMap.put(name,value);
        return this;
    }

    @Override
    public boolean removeParameter(String name)
    {
        return paramMap.remove(name) != null; // Si es null es que no existe porque no permitimos null,
    }

    @Override
    public long getLongParameter(String name, long defaultValue)
    {
         Object param = getParameter(name);
         if (param == null)
             return defaultValue;
         return (Long)param;
    }

    @Override
    public HttpParamMap setLongParameter(String name, long value)
    {
        return setParameter(name,Long.valueOf(value));
    }

    @Override
    public int getIntParameter(String name, int defaultValue)
    {
        Object param = getParameter(name);
        if (param == null)
            return defaultValue;
        return (Integer)param;
    }

    @Override
    public HttpParamMap setIntParameter(String name, int value)
    {
        return setParameter(name,Integer.valueOf(value));
    }

    @Override
    public double getDoubleParameter(String name, double defaultValue)
    {
        Object param = getParameter(name);
        if (param == null)
            return defaultValue;
        return (Double)param;
    }

    @Override
    public HttpParamMap setDoubleParameter(String name, double value)
    {
        return setParameter(name,Double.valueOf(value));
    }

    @Override
    public boolean getBooleanParameter(String name, boolean defaultValue)
    {
        Object param = getParameter(name);
        if (param == null)
            return defaultValue;
        return (Boolean)param;
    }

    @Override
    public HttpParamMap setBooleanParameter(String name, boolean value)
    {
        return setParameter(name,Boolean.valueOf(value));
    }

    @Override
    public boolean isParameterTrue(String name)
    {
        return getBooleanParameter(name,false);
    }

    @Override
    public boolean isParameterFalse(String name)
    {
        return !getBooleanParameter(name,false);
    }
}
