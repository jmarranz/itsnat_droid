package org.itsnat.droid.impl.dom;

import java.util.Comparator;

/**
 * Created by jmarranz on 15/01/2016.
 */
public class TimestampExtended
{
    protected long timestamp;
    protected int complementary; // En 1 ms pasan muchas cosas, necesitamos un complementario

    public TimestampExtended()
    {
        this.timestamp = System.currentTimeMillis();
        this.complementary = 0;
    }

    public TimestampExtended(TimestampExtended copy)
    {
        this.timestamp = copy.timestamp;
        this.complementary = copy.complementary;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }

    public int getComplementary()
    {
        return complementary;
    }

    public void incComplementary()
    {
        this.complementary++;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimestampExtended that = (TimestampExtended) o;

        if (timestamp != that.timestamp) return false;
        return complementary == that.complementary;
    }

    @Override
    public int hashCode()
    {
        int result = (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + complementary;
        return result;
    }

    public void update()
    {
        this.timestamp = System.currentTimeMillis();
        this.complementary = 0;
    }


}
