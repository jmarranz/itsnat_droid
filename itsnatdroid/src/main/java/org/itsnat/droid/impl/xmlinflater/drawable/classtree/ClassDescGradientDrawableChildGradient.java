package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.GradientDrawable;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBase;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableChildGradient;
import org.itsnat.droid.impl.xmlinflater.PercFloatImpl;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodColor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionPercFloat;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodFloat;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescGradientDrawableChildGradient extends ClassDescElementDrawableChildNormal<GradientDrawableChildGradient>
{
    public ClassDescGradientDrawableChildGradient(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"shape:gradient",null);
    }

    @Override
    public Class<GradientDrawableChildGradient> getDrawableOrElementDrawableClass()
    {
        return GradientDrawableChildGradient.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElemDrawable domElement, DOMElemDrawable domElementParent, ElementDrawableChildBase parentChildDrawable, AttrDrawableContext attrCtx)
    {
        return new GradientDrawableChildGradient(parentChildDrawable);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodFloat(this, "angle", 0f));
        addAttrDescAN(new AttrDescReflecMethodColor(this, "centerColor", 0));
        addAttrDescAN(new AttrDescReflecMethodDimensionPercFloat(this, "centerX", true, new PercFloatImpl(0.5f)));
        addAttrDescAN(new AttrDescReflecMethodDimensionPercFloat(this, "centerY", true, new PercFloatImpl(0.5f)));
        addAttrDescAN(new AttrDescReflecMethodColor(this, "endColor", 0));
        addAttrDescAN(new AttrDescReflecMethodDimensionPercFloat(this, "gradientRadius",true, new PercFloatImpl(0.5f)));
        addAttrDescAN(new AttrDescReflecMethodColor(this, "startColor", 0));
        addAttrDescAN(new AttrDescReflecMethodNameSingle(this, "type", int.class, GradientTypeUtil.nameValueMap, "linear"));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "useLevel", false));
    }

    public static class GradientTypeUtil
    {
        public static final MapSmart<String,Integer> nameValueMap = MapSmart.<String,Integer>create(3);
        static
        {
            nameValueMap.put("linear", GradientDrawable.LINEAR_GRADIENT);
            nameValueMap.put("radial", GradientDrawable.RADIAL_GRADIENT);
            nameValueMap.put("sweep", GradientDrawable.SWEEP_GRADIENT);
        }
    }

}
