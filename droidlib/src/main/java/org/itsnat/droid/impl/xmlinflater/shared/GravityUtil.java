package org.itsnat.droid.impl.xmlinflater.shared;

import org.itsnat.droid.impl.util.MapSmart;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by jmarranz on 28/07/14.
 */
public class GravityUtil
{
    public static final MapSmart<String,Integer> nameValueMap = MapSmart.<String,Integer>create( 14 );

    static
    {
        // Merece la pena un Map, de otra manera son muchos if anidados y poco rendimiento

        // http://developer.android.com/reference/android/widget/LinearLayout.LayoutParams.html#attr_android:layout_gravity
        // http://developer.android.com/reference/android/widget/LinearLayout.html#attr_android:gravity
        // http://developer.android.com/reference/android/widget/TextView.html#attr_android:gravity
        // http://developer.android.com/reference/android/widget/FrameLayout.html#attr_android:foregroundGravity
        // Tambien en drawables
        // http://developer.android.com/reference/android/graphics/drawable/BitmapDrawable.html#attr_android:gravity

        nameValueMap.put("top", 0x30);
        nameValueMap.put("bottom", 0x50);
        nameValueMap.put("left", 0x03);
        nameValueMap.put("right", 0x05);
        nameValueMap.put("center_vertical", 0x10);
        nameValueMap.put("fill_vertical", 0x70);
        nameValueMap.put("center_horizontal", 0x01);
        nameValueMap.put("fill_horizontal", 0x07);
        nameValueMap.put("center", 0x11);
        nameValueMap.put("fill", 0x77);
        nameValueMap.put("clip_vertical", 0x80);
        nameValueMap.put("clip_horizontal", 0x08);
        nameValueMap.put("start", 0x00800003);
        nameValueMap.put("end", 0x00800005);

        // En el caso del atributo FrameLayout android:foregroundGravity, los casos start y end no aplican según los javadoc pero mi impresión es que es un olvido, de todas formas el programador obediente no debería usar entonces esos casos "no documentados" en el javadoc

    }

    /*
    private static final MapSmart<Integer,String> valueNameMap = MapSmart.<Integer,String>create( 14 );

    static
    {
        valueNameMap.put(0x30,"top");
        valueNameMap.put(0x50,"bottom");
        valueNameMap.put(0x03,"left");
        valueNameMap.put(0x05,"right");
        valueNameMap.put(0x10,"center_vertical");
        valueNameMap.put(0x70,"fill_vertical");
        valueNameMap.put(0x01,"center_horizontal");
        valueNameMap.put(0x07,"fill_horizontal");
        valueNameMap.put(0x11,"center");
        valueNameMap.put(0x77,"fill");
        valueNameMap.put(0x80,"clip_vertical");
        valueNameMap.put(0x08,"clip_horizontal");
        valueNameMap.put(0x00800003,"start");
        valueNameMap.put(0x00800005,"end");
    }
    */

    public static String getNameFromValue(String value)
    {
        // Ej 0x30 | 0x50 => "top|bottom"
        StringBuilder nameRes = new StringBuilder();
        boolean empty = true;
        int valueInt = Integer.valueOf(value);
        for(Iterator<Map.Entry<String,Integer>> it = nameValueMap.iterator(); it.hasNext(); )
        {
            Map.Entry<String,Integer> entry = it.next();
            String currName = entry.getKey();
            int currValue = entry.getValue();
            int currValueFiltered = valueInt & currValue;
            if (currValueFiltered == currValue)
            {
                if (empty) empty = false;
                else nameRes.append('|');
                nameRes.append(currName);
            }
        }
        return nameRes.toString();
    }
}
