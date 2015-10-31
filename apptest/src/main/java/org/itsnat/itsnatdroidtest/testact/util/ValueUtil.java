package org.itsnat.itsnatdroidtest.testact.util;

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
    public static float toPixelFloat(int unit,float value, Resources res)
    {
        // Nexus 4 tiene un scale 2 de dp a px (xhdpi),  con un valor de 0.3 devuelve 0.6 bien para probar si usar round/floor
        // Nexus 5 tiene un scale 3 de dp a px (xxhdpi), con un valor de 0.3 devuelve 0.9 bien para probar si usar round/floor
        // La VM ItsNatDroid es una Nexus 4
        return TypedValue.applyDimension(unit, value, res.getDisplayMetrics());
    }

    public static float toPixelFloatFloor(int unit,float value, Resources res)
    {
        float valuePx = toPixelFloat(unit, value, res);
        valuePx = (float)Math.floor(valuePx);
        return valuePx;
    }

    public static float toPixelFloatRound(int unit,float value, Resources res)
    {
        float valuePx = toPixelFloat(unit, value, res);
        valuePx = Math.round(valuePx);
        return valuePx;
    }

    public static float dpToPixelFloat(float value, Resources res)
    {
        return toPixelFloat(TypedValue.COMPLEX_UNIT_DIP, value,res);
    }

    public static float dpToPixelFloatFloor(float value, Resources res)
    {
        return toPixelFloatFloor(TypedValue.COMPLEX_UNIT_DIP, value, res);
    }

    public static float dpToPixelFloatRound(float value, Resources res)
    {
        return toPixelFloatRound(TypedValue.COMPLEX_UNIT_DIP, value, res);
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



}
