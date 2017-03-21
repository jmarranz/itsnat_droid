package org.itsnat.droid.impl.xmlinflater.drawable.classtree.child;

import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildNormal;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;

/**
 * Created by jmarranz on 10/11/14.
 */
public abstract class ClassDescElementDrawableChildNormal<TelementDrawableChild extends ElementDrawableChildNormal> extends ClassDescElementDrawableChildBased<TelementDrawableChild>
{
    public ClassDescElementDrawableChildNormal(ClassDescDrawableMgr classMgr, String elemName, ClassDescElementDrawableChildBased<? super TelementDrawableChild> parentClass)
    {
        super(classMgr,elemName,parentClass);
    }

}

