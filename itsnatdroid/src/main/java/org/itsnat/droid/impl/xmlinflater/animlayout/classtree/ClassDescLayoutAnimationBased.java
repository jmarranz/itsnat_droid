package org.itsnat.droid.impl.xmlinflater.animlayout.classtree;

import android.view.animation.LayoutAnimationController;

import org.itsnat.droid.impl.xmlinflater.animlayout.AttrLayoutAnimationContext;
import org.itsnat.droid.impl.xmlinflater.animlayout.ClassDescLayoutAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDescResourceBased;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescLayoutAnimationBased<T extends LayoutAnimationController> extends ClassDescResourceBased<T,AttrLayoutAnimationContext>
{
    public ClassDescLayoutAnimationBased(ClassDescLayoutAnimationMgr classMgr, String tagName, ClassDescLayoutAnimationBased<? super T> parentClass)
    {
        super(classMgr, tagName, parentClass);
    }

    public ClassDescLayoutAnimationMgr getClassDescLayoutAnimationMgr()
    {
        return (ClassDescLayoutAnimationMgr) classMgr;
    }

    @SuppressWarnings("unchecked")
    public ClassDescLayoutAnimationBased<LayoutAnimationController> getParentClassDescLayoutAnimationBased()
    {
        return (ClassDescLayoutAnimationBased<LayoutAnimationController>) getParentClassDescResourceBased(); // Puede ser null
    }

    @Override
    protected void init()
    {
        //initClass();

        super.init();
    }

}
