package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.DrawableContainer;

import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;

/**
 * Created by jmarranz on 10/11/14.
 */
public abstract class ClassDescDrawableContainerBased<Tdrawable extends DrawableContainer> extends ClassDescElementDrawableRoot<Tdrawable> implements ClassDescCallback<Tdrawable>
{
    public ClassDescDrawableContainerBased(ClassDescDrawableMgr classMgr,String elemName,ClassDescDrawable<? super Tdrawable> parentClass)
    {
        super(classMgr,elemName,parentClass);
    }

}
