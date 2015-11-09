package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemGradient;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodColor;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodDimensionPercFloat;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodFloat;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodSingleName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescGradientDrawableItemGradient extends ClassDescElementDrawableChildNormal<GradientDrawableItemGradient>
{
    public ClassDescGradientDrawableItemGradient(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"shape:gradient");
    }

    @Override
    public Class<GradientDrawableItemGradient> getDrawableOrElementDrawableClass()
    {
        return GradientDrawableItemGradient.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElement domElement, DOMElement domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        return new GradientDrawableItemGradient(parentChildDrawable);
    }

    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescDrawableReflecMethodFloat<GradientDrawableItemGradient>(this, "angle"));
        addAttrDesc(new AttrDescDrawableReflecMethodColor<GradientDrawableItemGradient>(this, "centerColor"));
        addAttrDesc(new AttrDescDrawableReflecMethodDimensionPercFloat<GradientDrawableItemGradient>(this, "centerX"));
        addAttrDesc(new AttrDescDrawableReflecMethodDimensionPercFloat<GradientDrawableItemGradient>(this, "centerY"));
        addAttrDesc(new AttrDescDrawableReflecMethodColor<GradientDrawableItemGradient>(this, "endColor"));
        addAttrDesc(new AttrDescDrawableReflecMethodDimensionPercFloat<GradientDrawableItemGradient>(this, "gradientRadius"));
        addAttrDesc(new AttrDescDrawableReflecMethodColor<GradientDrawableItemGradient>(this, "startColor"));
        addAttrDesc(new AttrDescDrawableReflecMethodSingleName<Integer,String>(this, "type", int.class, GradientTypeUtil.valueMap));
        addAttrDesc(new AttrDescDrawableReflecMethodBoolean<GradientDrawableItemGradient>(this, "useLevel"));
    }

    public static class GradientTypeUtil
    {
        public static final Map<String, Integer> valueMap = new HashMap<String, Integer>(3);

        static
        {
            valueMap.put("linear", GradientDrawable.LINEAR_GRADIENT);
            valueMap.put("radial", GradientDrawable.RADIAL_GRADIENT);
            valueMap.put("sweep",  GradientDrawable.SWEEP_GRADIENT);
        }
    }

}
