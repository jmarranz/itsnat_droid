package org.itsnat.droid.impl.util;

import android.content.res.Resources;
import android.util.TypedValue;

import org.itsnat.droid.ItsNatDroidException;

import java.io.UnsupportedEncodingException;

/**
 * Se debería usar TypedValue.complexToDimensionPixelOffset y complexToDimensionPixelSize
 * en el caso de necesitar enteros pero es un follón
 *
 * Algunos métodos se usan en tests no en el framework
 *
 * Created by jmarranz on 30/04/14.
 */
public class ValueUtil
{
    private static float toPixelFloat(int unit,float value, Resources res)
    {
        return TypedValue.applyDimension(unit, value, res.getDisplayMetrics());
    }

    private static float toPixelFloatFloor(int unit,float value, Resources res)
    {
        float valuePx = toPixelFloat(unit, value, res);
        valuePx = (float)Math.floor(valuePx);
        return valuePx;
    }

    public static float dpToPixelFloat(float value, Resources res)
    {
        return toPixelFloat(TypedValue.COMPLEX_UNIT_DIP, value,res);
    }

    public static float spToPixelFloat(float value, Resources res)
    {
        return toPixelFloat(TypedValue.COMPLEX_UNIT_SP, value, res);
    }

    public static float inToPixelFloat(float value, Resources res)
    {
        return toPixelFloat(TypedValue.COMPLEX_UNIT_IN, value, res);
    }

    public static float mmToPixelFloat(float value, Resources res)
    {
        return toPixelFloat(TypedValue.COMPLEX_UNIT_MM, value, res);
    }

    public static float dpToPixelFloatFloor(float value, Resources res)
    {
        return toPixelFloatFloor(TypedValue.COMPLEX_UNIT_DIP, value, res);
    }

    public static float spToPixelFloatFloor(float value, Resources res)
    {
        return toPixelFloatFloor(TypedValue.COMPLEX_UNIT_SP, value, res);
    }

    public static float inToPixelFloatFloor(float value, Resources res)
    {
        return toPixelFloatFloor(TypedValue.COMPLEX_UNIT_IN, value, res);
    }

    public static float mmToPixelFloatFloor(float value, Resources res)
    {
        return toPixelFloatFloor(TypedValue.COMPLEX_UNIT_MM, value, res);
    }

    public static int dpToPixelIntRound(float value, Resources res)
    {
        // Para ver si el redondeo es correcto conviene elegir valores tal que tras aplicar la escala dpToPixelFloatFloor quede X.Y donde Y >= 5, ej 1.3 dp escala 2x => 2.6 px (1.9 si es 3x)
        float valuePx = dpToPixelFloat(value, res);
        int valuePxInt = Math.round(valuePx);
/*
        if (valuePxInt != (int)valuePx)
            res.getClass();
        else
            res.getClass();
*/
        return valuePxInt;
    }

    public static int dpToPixelIntFloor(float value, Resources res)
    {
        // Para ver si el redondeo es correcto conviene elegir valores tal que al aplicar la escala de X.Y donde Y >= 5, ej 1.3dp escala 2x => 1.6 (1.9 si es 3x)
        float valuePx = dpToPixelFloat(value, res);
        int valuePxInt = (int)valuePx;
/*
        if (valuePxInt != valuePx)
            res.getClass();
        else
            res.getClass();
*/
        return valuePxInt;
    }


    public static boolean isEmpty(String str)
    {
        return str == null || str.isEmpty();
    }

    public static String toString(byte[] data,String encoding)
    {
        try { return new String(data,encoding); }
        catch (UnsupportedEncodingException ex) { throw new ItsNatDroidException(ex); }
    }

    public static boolean equalsNullAllowed(Object value1, Object value2)
    {
        if (value1 != null)
            return value1.equals(value2);
        else if (value2 != null)
            return false;
        else
            return true; // Los dos son null
    }

    public static boolean equalsEmptyAllowed(String value1, String value2)
    {
        if (isEmpty(value1))  // null y "" son iguales en este caso
            return isEmpty(value2);
        else
            return value1.equals(value2);
    }

}
