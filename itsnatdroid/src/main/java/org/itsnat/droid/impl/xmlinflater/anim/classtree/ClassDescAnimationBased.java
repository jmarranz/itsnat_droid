package org.itsnat.droid.impl.xmlinflater.anim.classtree;

import android.view.animation.Animation;

import org.itsnat.droid.impl.xmlinflater.anim.AttrAnimationContext;
import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDescResourceBased;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescAnimationBased<T extends Animation> extends ClassDescResourceBased<T,AttrAnimationContext>
{
    public ClassDescAnimationBased(ClassDescAnimationMgr classMgr, String tagName, ClassDescAnimationBased<? super T> parentClass)
    {
        super(classMgr, tagName, parentClass);
    }

    public ClassDescAnimationMgr getClassDescAnimationMgr()
    {
        return (ClassDescAnimationMgr) classMgr;
    }

    @SuppressWarnings("unchecked")
    public ClassDescAnimationBased<Animation> getParentClassDescAnimationBased()
    {
        return (ClassDescAnimationBased<Animation>) getParentClassDescResourceBased(); // Puede ser null
    }

    @Override
    protected void init()
    {
        //initClass();

        super.init();
    }

}
