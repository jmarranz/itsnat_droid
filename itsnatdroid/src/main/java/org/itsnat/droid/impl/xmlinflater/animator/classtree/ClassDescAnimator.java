package org.itsnat.droid.impl.xmlinflater.animator.classtree;

import android.animation.Animator;
import android.content.Context;

import org.itsnat.droid.impl.xmlinflater.animator.ClassDescAnimatorMgr;

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
    protected Animator createAnimatorObject(Context ctx)
    {
        return null; // No se necesita
    }
}
