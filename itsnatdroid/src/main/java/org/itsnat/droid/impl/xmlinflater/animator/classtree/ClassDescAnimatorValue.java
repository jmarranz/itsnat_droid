package org.itsnat.droid.impl.xmlinflater.animator.classtree;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.animator.ClassDescAnimatorMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.OrientationUtil;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimatorValue extends ClassDescAnimatorBased<ValueAnimator>
{
    public static final MapSmart<String,Integer> repeatModeMap = MapSmart.<String,Integer>create(2);
    static
    {
        repeatModeMap.put("repeat", ValueAnimator.RESTART);
        repeatModeMap.put("reverse", ValueAnimator.REVERSE);
    }

    public ClassDescAnimatorValue(ClassDescAnimatorMgr classMgr, ClassDescAnimatorBased<? super Animator> parentClass)
    {
        super(classMgr, "animator", parentClass);
    }

    @Override
    public Class<ValueAnimator> getDeclaredClass()
    {
        return ValueAnimator.class;
    }

    @Override
    protected ValueAnimator createAnimatorObject(Context ctx)
    {
        return new ValueAnimator();
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodInt(this, "repeatCount", 0)); // Se puede llamar independientemente de valueFrom y valueTo
        addAttrDescAN(new AttrDescReflecMethodNameSingle(this, "repeatMode", int.class, repeatModeMap, "repeat"));  // "
    }

}
