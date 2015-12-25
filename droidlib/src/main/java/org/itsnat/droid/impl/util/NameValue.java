package org.itsnat.droid.impl.util;

/**
 * Created by Jose on 15/12/2015.
 */
public class NameValue
{
    protected String name;
    protected Object value;

    public NameValue(String name, Object value)
    {
        this.name = name;
        this.value = value;
    }

    public String getName()
    {
        return name;
    }

    public Object getValue()
    {
        return value;
    }
}
