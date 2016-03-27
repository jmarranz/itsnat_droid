package org.itsnat.droid.impl.xmlinflater.animator.classtree;

import android.animation.Animator;
import android.content.Context;

import org.itsnat.droid.impl.xmlinflater.animator.ClassDescAnimatorMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodLong;

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
    protected Animator createAnimatorNative(Context ctx)
    {
        return null; // No se necesita, Animator es abstract
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodLong(this, "duration", 0L)); // Se puede llamar independientemente de valueFrom y valueTo
        addAttrDescAN(new AttrDescReflecMethodLong(this, "startOffset", "setStartDelay", 0L)); // "
    }
}
