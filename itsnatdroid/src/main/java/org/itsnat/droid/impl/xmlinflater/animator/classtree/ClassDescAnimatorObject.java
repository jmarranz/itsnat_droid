package org.itsnat.droid.impl.xmlinflater.animator.classtree;

import android.animation.ObjectAnimator;
import android.content.Context;

import org.itsnat.droid.impl.xmlinflater.animator.ClassDescAnimatorMgr;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimatorObject extends ClassDescAnimatorBased<ObjectAnimator>
{
    public ClassDescAnimatorObject(ClassDescAnimatorMgr classMgr, ClassDescAnimatorValue parentClass)
    {
        super(classMgr, "objectAnimator", parentClass);
    }

    @Override
    protected ObjectAnimator createAnimatorObject(Context ctx)
    {
        return new ObjectAnimator();
    }
}
