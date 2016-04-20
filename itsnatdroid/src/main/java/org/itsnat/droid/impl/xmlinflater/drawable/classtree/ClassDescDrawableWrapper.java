package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;

/**
 * No podemos usar explícitamente DrawableWrapper pues es level 23 (no 15) pero vamos preparándonos ya
 * Created by jmarranz on 10/11/14.
 */
public abstract class ClassDescDrawableWrapper<Tdrawable extends Drawable> extends ClassDescElementDrawableRoot<Tdrawable> implements ClassDescCallback<Tdrawable>
{
    public ClassDescDrawableWrapper(ClassDescDrawableMgr classMgr, String elemName)
    {
        super(classMgr,elemName);
    }

}
