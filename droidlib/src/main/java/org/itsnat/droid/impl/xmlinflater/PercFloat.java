package org.itsnat.droid.impl.xmlinflater;

import android.util.TypedValue;

/**
 * Created by Jose on 04/11/2015.
 */
public class PercFloat
{

    protected int dataType; // TypedValue.TYPE_FRACTION, TypedValue.TYPE_FLOAT
    protected boolean fractionParent; // Sólo útil en caso de TYPE_FRACTION
    protected float value;

    public PercFloat(int dataType,boolean fractionParent,float value)
    {
        this.dataType = dataType;
        this.fractionParent = fractionParent;
        this.value = value;
    }

    public int getDataType() {
        return dataType;
    }

    public boolean isFractionParent()
    {
        return fractionParent;
    }

    public float getValue() {
        return value;
    }
}
