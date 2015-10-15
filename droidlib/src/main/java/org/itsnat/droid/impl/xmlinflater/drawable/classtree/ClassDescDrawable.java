package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableOrElementDrawableChildMgr;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class ClassDescDrawable<Tdrawable extends Drawable> extends ClassDescDrawableOrElementDrawableChild
{
    public ClassDescDrawable(ClassDescDrawableOrElementDrawableChildMgr classMgr, String className, ClassDescDrawable parentClass)
    {
        super(classMgr, className, parentClass);
    }


    @SuppressWarnings("unchecked")
    public ClassDescDrawable getParentClassDescDrawable()
    {
        return (ClassDescDrawable) getParentClassDesc();
    }


}
