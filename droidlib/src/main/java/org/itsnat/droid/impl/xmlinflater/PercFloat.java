package org.itsnat.droid.impl.xmlinflater;

import android.util.TypedValue;

import org.itsnat.droid.ItsNatDroidException;

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

    public PercFloat(float value)
    {
        this(TypedValue.TYPE_FLOAT,false,value); // fractionParent es indiferente en este caso
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

    public float toFloatBasedOnDataType()
    {
        float valueTmp = this.value;
        if (dataType == TypedValue.TYPE_FRACTION)
        {
            valueTmp = valueTmp / 100;
        }
        else if (dataType != TypedValue.TYPE_FLOAT) throw new ItsNatDroidException("Internal Error");
        return valueTmp;
    }
}
