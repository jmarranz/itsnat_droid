package org.itsnat.droid;

/**
 * Created by jmarranz on 12/02/2017.
 */

public interface PercFloat
{
    public int getDataType();

    public boolean isFraction();

    public boolean isFractionParent();

    public float getValue();

    public float toFloatBasedOnDataType();
}
