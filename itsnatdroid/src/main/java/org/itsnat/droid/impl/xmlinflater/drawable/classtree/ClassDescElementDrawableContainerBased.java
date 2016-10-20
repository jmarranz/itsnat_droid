package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.DrawableContainer;

import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;

/**
 * Created by jmarranz on 27/11/14.
 */
public abstract class ClassDescElementDrawableContainerBased<Tdrawable extends DrawableContainer> extends ClassDescElementDrawableBased<Tdrawable> implements ClassDescCallback
{
    public ClassDescElementDrawableContainerBased(ClassDescDrawableMgr classMgr,String tagName,ClassDescElementDrawableBased<? super DrawableContainer> parent)
    {
        super(classMgr,tagName,parent);
    }

}

