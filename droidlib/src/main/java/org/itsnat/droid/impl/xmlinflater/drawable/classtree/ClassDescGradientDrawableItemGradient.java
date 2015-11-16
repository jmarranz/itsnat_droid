package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemGradient;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodDimensionPercFloat;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodFloat;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodSingleName;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodColor;

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
        addAttrDesc(new AttrDescReflecMethodColor(this, "centerColor",0));
        addAttrDesc(new AttrDescDrawableReflecMethodDimensionPercFloat<GradientDrawableItemGradient>(this, "centerX"));
        addAttrDesc(new AttrDescDrawableReflecMethodDimensionPercFloat<GradientDrawableItemGradient>(this, "centerY"));
        addAttrDesc(new AttrDescReflecMethodColor(this, "endColor",0));
        addAttrDesc(new AttrDescDrawableReflecMethodDimensionPercFloat<GradientDrawableItemGradient>(this, "gradientRadius"));
        addAttrDesc(new AttrDescReflecMethodColor(this, "startColor",0));
        addAttrDesc(new AttrDescDrawableReflecMethodSingleName<Integer,String>(this, "type", int.class, GradientTypeUtil.valueMap));
        addAttrDesc(new AttrDescReflecMethodBoolean(this, "useLevel",false));
    }

    public static class GradientTypeUtil
    {
        @SuppressWarnings("unchecked")
        public static final MapSmart<String,Integer> valueMap = MapSmart.<String,Integer>create(3);

        static
        {
            valueMap.put("linear", GradientDrawable.LINEAR_GRADIENT);
            valueMap.put("radial", GradientDrawable.RADIAL_GRADIENT);
            valueMap.put("sweep",  GradientDrawable.SWEEP_GRADIENT);
        }
    }

}
