package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import android.content.Context;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescDrawable_GradientDrawable_shape extends AttrDescDrawableReflecMethodSingleName<Integer, GradientDrawable>
{
    public static final Map<String,Integer> valueMap = new HashMap<String,Integer>( 4 );
    static
    {
        valueMap.put("rectangle",0);
        valueMap.put("oval",1);
        valueMap.put("line",2);
        valueMap.put("ring",3);
    }

    public AttrDescDrawable_GradientDrawable_shape(ClassDescDrawable parent)
    {
        super(parent,"shape",int.class,valueMap);
    }

    @Override
    public void setAttribute(GradientDrawable draw, DOMAttr attr,XMLInflaterDrawable xmlInflaterDrawable,Context ctx)
    {
        int shape = this.<Integer>parseSingleName(attr.getValue(), valueMap); // Valor concreto no puede ser un recurso

        draw.setShape(shape);
    }
}
