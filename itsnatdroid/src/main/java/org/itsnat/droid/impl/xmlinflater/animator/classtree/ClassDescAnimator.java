package org.itsnat.droid.impl.xmlinflater.animator.classtree;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;

import org.itsnat.droid.impl.xmlinflater.animator.ClassDescAnimatorMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimator extends ClassDescAnimatorBased<Animator>
{
    public ClassDescAnimator(ClassDescAnimatorMgr classMgr)
    {
        super(classMgr, "NONE", null);
    }

    @Override
    public Class<Animator> getDeclaredClass()
    {
        return Animator.class;
    }


    @Override
    protected Animator createAnimatorObject(Context ctx)
    {
        return null; // No se necesita
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodInt(this, "duration", 0)); // Se puede llamar independientemente de valueFrom y valueTo
        addAttrDescAN(new AttrDescReflecMethodInt(this, "startOffset", "setStartDelay", 0)); // "
    }
}
