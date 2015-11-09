package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.DrawableContainer;

import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;

/**
 * Created by jmarranz on 10/11/14.
 */
public abstract class ClassDescDrawableContainer<Tdrawable extends DrawableContainer> extends ClassDescElementDrawableRoot<Tdrawable>
{
    public ClassDescDrawableContainer(ClassDescDrawableMgr classMgr,String elemName)
    {
        super(classMgr,elemName);
    }

}
