package org.itsnat.droid.impl.domparser;

import org.itsnat.droid.impl.dom.TimestampExtended;

/**
 * Created by jmarranz on 03/04/2016.
 */
public class ResourceTimestamped<T>
{
    protected String key;
    protected T resource;
    protected TimestampExtended timestampExt;

    public ResourceTimestamped(String key,T resource)
    {
        this.key = key;
        this.resource = resource;
        this.timestampExt = new TimestampExtended();
    }

    public String getKey()
    {
        return key;
    }

    public TimestampExtended getTimestampExtended()
    {
        return timestampExt;
    }

    public T getResource()
    {
        return resource;
    }
}
