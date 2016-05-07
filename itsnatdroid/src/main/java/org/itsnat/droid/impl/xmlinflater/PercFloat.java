package org.itsnat.droid.impl.xmlinflater;

import android.util.TypedValue;

import org.itsnat.droid.impl.util.MiscUtil;

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

        if (dataType != TypedValue.TYPE_FRACTION && dataType != TypedValue.TYPE_FLOAT) throw MiscUtil.internalError();
    }

    public PercFloat(float value)
    {
        this(TypedValue.TYPE_FLOAT,false,value); // fractionParent es indiferente en este caso
    }

    public int getDataType() {
        return dataType;
    }

    public boolean isFraction()
    {
        return dataType == TypedValue.TYPE_FRACTION;
    }

    public boolean isFractionParent()
    {
        return fractionParent; // Sólo interesa cuando es
    }

    public float getValue() {
        return value;
    }

    public float toFloatBasedOnDataType()
    {
        if (dataType == TypedValue.TYPE_FRACTION)
        {
            return this.value / 100;
        }
        else if (dataType == TypedValue.TYPE_FLOAT)
        {
            return this.value;
        }
        else throw MiscUtil.internalError();
    }

}
