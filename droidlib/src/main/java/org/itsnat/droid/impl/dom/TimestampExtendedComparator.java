package org.itsnat.droid.impl.dom;

import org.itsnat.droid.impl.dom.TimestampExtended;

import java.util.Comparator;

/**
 * Created by jmarranz on 15/01/2016.
 */
public class TimestampExtendedComparator implements Comparator<TimestampExtended>
{
    @Override
    public int compare(TimestampExtended lhs, TimestampExtended rhs)
    {
        if (lhs == rhs)
            return 0;

        if (lhs.timestamp < rhs.timestamp)
            return -1;
        else if (lhs.timestamp > rhs.timestamp)
            return +1;
        else // timestamp igual
        {
            if (lhs.complementary < rhs.complementary)
                return -1;
            else if (lhs.complementary > rhs.complementary)
                return +1;
            else
                return 0;
        }
    }
}
