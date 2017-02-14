package org.itsnat.droid.impl.xmlinflater.animlayout.classtree;

import android.content.Context;
import android.view.animation.LayoutAnimationController;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.PercFloatImpl;
import org.itsnat.droid.impl.xmlinflater.animlayout.ClassDescLayoutAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.animlayout.attr.AttrDescLayoutAnimation_LayoutAnimationController_animation;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInterpolator;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodPercFloat;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescLayoutAnimation extends ClassDescLayoutAnimationBased<LayoutAnimationController>
{
    public static final MapSmart<String,Integer> animationOrderMap = MapSmart.<String,Integer>create(3);
    static
    {
        animationOrderMap.put("normal", LayoutAnimationController.ORDER_NORMAL);
        animationOrderMap.put("reverse",LayoutAnimationController.ORDER_REVERSE);
        animationOrderMap.put("random", LayoutAnimationController.ORDER_RANDOM);
    }

    public ClassDescLayoutAnimation(ClassDescLayoutAnimationMgr classMgr)
    {
        super(classMgr, "layoutAnimation", null);
    }

    @Override
    public Class<LayoutAnimationController> getDeclaredClass()
    {
        return LayoutAnimationController.class;
    }

    @Override
    protected LayoutAnimationController createResourceNative(Context ctx)
    {
        return new LayoutAnimationController(ctx,null);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescLayoutAnimation_LayoutAnimationController_animation(this));
        addAttrDescAN(new AttrDescReflecMethodNameSingle(this, "animationOrder","setOrder", int.class, animationOrderMap, "normal"));
        addAttrDescAN(new AttrDescReflecMethodPercFloat(this,"delay",false,new PercFloatImpl(0)));
        addAttrDescAN(new AttrDescReflecMethodInterpolator(this, "interpolator","@null"));
    }
}

