package org.itsnat.droid.impl.xmlinflater.animinterp.classtree;

import android.content.Context;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.anim.attr.AttrDescAnimation_view_animation_Animation_repeatCount;
import org.itsnat.droid.impl.xmlinflater.anim.attr.AttrDescAnimation_view_animation_TranslateAnimation_fromToXYDelta;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimationBased;
import org.itsnat.droid.impl.xmlinflater.animinterp.ClassDescInterpolatorMgr;
import org.itsnat.droid.impl.xmlinflater.animinterp.attr.AttrDescInterpolator_AccelerateInterpolator_factor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodColor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInterpolator;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodLong;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescInterpolatorAccelerate extends ClassDescInterpolatorBased<AccelerateInterpolator>
{
    public ClassDescInterpolatorAccelerate(ClassDescInterpolatorMgr classMgr)
    {
        super(classMgr, "accelerateInterpolator", null);
    }

    @Override
    public Class<AccelerateInterpolator> getDeclaredClass()
    {
        return AccelerateInterpolator.class;
    }

    @Override
    protected AccelerateInterpolator createResourceNative(Context ctx)
    {
        return new AccelerateInterpolator(ctx,null);
    }

    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescInterpolator_AccelerateInterpolator_factor(this, "factor"));

    }
}

