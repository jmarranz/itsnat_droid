package org.itsnat.droid.impl.dommini;

/**
 * Created by jmarranz on 10/02/2016.
 */
public class DMAttr extends DMNode
{
    protected String name;
    protected String value;

    public DMAttr(String name,String value)
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
