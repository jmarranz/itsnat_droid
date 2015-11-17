package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemGradient;
import org.itsnat.droid.impl.xmlinflater.PercFloat;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodColor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionPercFloat;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodFloat;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodSingleName;

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

        addAttrDesc(new AttrDescReflecMethodFloat(this, "angle",0f));
        addAttrDesc(new AttrDescReflecMethodColor(this, "centerColor",0));
        addAttrDesc(new AttrDescReflecMethodDimensionPercFloat(this, "centerX",new PercFloat(0.5f)));
        addAttrDesc(new AttrDescReflecMethodDimensionPercFloat(this, "centerY",new PercFloat(0.5f)));
        addAttrDesc(new AttrDescReflecMethodColor(this, "endColor",0));
        addAttrDesc(new AttrDescReflecMethodDimensionPercFloat(this, "gradientRadius",new PercFloat(0.5f)));
        addAttrDesc(new AttrDescReflecMethodColor(this, "startColor",0));
        addAttrDesc(new AttrDescReflecMethodSingleName(this, "type", int.class, GradientTypeUtil.valueMap,"linear"));
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
