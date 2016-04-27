package org.itsnat.droid.impl.xmlinflater.anim.classtree;

import android.animation.Animator;
import android.content.Context;
import android.view.animation.Animation;

import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.animator.ClassDescAnimatorMgr;
import org.itsnat.droid.impl.xmlinflater.animator.classtree.ClassDescAnimatorBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodLong;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimation extends ClassDescAnimationBased<Animation>
{
    public ClassDescAnimation(ClassDescAnimationMgr classMgr)
    {
        super(classMgr, "NONE", null);
    }

    @Override
    public Class<Animation> getDeclaredClass()
    {
        return Animation.class;
    }


    @Override
    protected Animation createAnimationNative(Context ctx)
    {
        return null; // No se necesita, Animation es abstract
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        //addAttrDescAN(new AttrDescReflecMethodLong(this, "duration", 0L)); // Se puede llamar independientemente de valueFrom y valueTo
        //addAttrDescAN(new AttrDescReflecMethodLong(this, "startOffset", "setStartDelay", 0L)); // "
    }
}
