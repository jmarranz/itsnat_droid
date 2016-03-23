package org.itsnat.droid.impl.xmlinflater.animator.classtree;

import android.animation.ValueAnimator;
import android.content.Context;

import org.itsnat.droid.impl.xmlinflater.animator.ClassDescAnimatorMgr;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimatorValue extends ClassDescAnimatorBased<ValueAnimator>
{
    public ClassDescAnimatorValue(ClassDescAnimatorMgr classMgr, ClassDescAnimator parentClass)
    {
        super(classMgr, "animator", parentClass);
    }

    @Override
    protected ValueAnimator createAnimatorObject(Context ctx)
    {
        return new ValueAnimator();
    }
}
