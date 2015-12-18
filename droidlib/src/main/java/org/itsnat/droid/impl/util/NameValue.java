package org.itsnat.droid.impl.util;

/**
 * Created by Jose on 15/12/2015.
 */
public class NameValue<V>
{
    protected String name;
    protected String value;

    public NameValue(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }
}
