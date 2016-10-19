package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildWithDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;

/**
 * Created by jmarranz on 10/11/14.
 */
public abstract class ClassDescElementDrawableChildWithDrawable<TelementDrawableChild extends ElementDrawableChildWithDrawable> extends ClassDescElementDrawableChildBased<TelementDrawableChild>
{
    public ClassDescElementDrawableChildWithDrawable(ClassDescDrawableMgr classMgr, String elemName, ClassDescElementDrawableChildBased<? super TelementDrawableChild> parentClass)
    {
        super(classMgr,elemName,parentClass);
    }

}

