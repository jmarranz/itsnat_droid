package org.itsnat.droid.impl.util;

import android.os.Build;

import org.itsnat.droid.ItsNatDroidException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmarranz on 15/09/14.
 */
public class MiscUtil
{
    // http://developer.android.com/reference/android/os/Build.VERSION_CODES.html

    public static final int ICE_CREAM_SANDWICH_MR1 = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1; // 15
    public static final int LOLLIPOP = 21;          // 21 == Build.VERSION_CODES.LOLLIPOP  (5.0.1)
    public static final int LOLLIPOP_MR1 = 22;   // 22 == Build.VERSION_CODES.LOLLIPOP_MR1 (5.1.1)
    public static final int MARSHMALLOW = 23;      // 23 == Build.VERSION_CODES.M  (6)

    private static final Map<String,Class> mapClasses = new HashMap<String,Class>();

    public static Class<?> resolveClass(String className)
    {
        // El propio Class.forName tiene obviamente un caché de clases ya cargadas (ojo de t_odo el espacio de clases Android),
        // sin embargo con nuestro Map se consiguen
        // mayores velocidades en clases ya cargadas si el map no se llena de muchas clases (menos de 50 por ejemplo)
        // Por supuesto NO utilizar multihilo
        Class<?> clasz = mapClasses.get(className);
        if (clasz != null)
            return clasz;

        try { clasz = Class.forName(className); }
        catch (ClassNotFoundException ex) { throw new ItsNatDroidException(ex); }
        mapClasses.put(className,clasz);
        return clasz;
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

    public static void waitPlease(long lapse)
    {
        try { Thread.sleep(lapse); }
        catch (InterruptedException ex) { throw new ItsNatDroidException(ex); }
    }
}
