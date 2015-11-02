package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;

import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableContainer;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;

import java.util.ArrayList;

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
