package org.itsnat.droid.impl.xmlinflater.animator.classtree;

import android.animation.Animator;

import org.itsnat.droid.impl.xmlinflater.animator.AttrAnimatorContext;
import org.itsnat.droid.impl.xmlinflater.animator.ClassDescAnimatorMgr;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDescResourceBased;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescAnimatorBased<T extends Animator> extends ClassDescResourceBased<T,AttrAnimatorContext>
{
    public ClassDescAnimatorBased(ClassDescAnimatorMgr classMgr, String tagName, ClassDescAnimatorBased<? super T> parentClass)
    {
        super(classMgr, tagName, parentClass);
    }

    public ClassDescAnimatorMgr getClassDescAnimatorMgr()
    {
        return (ClassDescAnimatorMgr) classMgr;
    }

    @SuppressWarnings("unchecked")
    public ClassDescAnimatorBased<Animator> getParentClassDescAnimatorBased()
    {
        return (ClassDescAnimatorBased<Animator>) getParentClassDescResourceBased(); // Puede ser null
    }


    @Override
    protected void init()
    {
        //initClass();

        super.init();
    }

}
