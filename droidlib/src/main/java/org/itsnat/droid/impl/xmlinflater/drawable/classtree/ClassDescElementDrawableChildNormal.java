package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBridge;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildNormal;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableOrElementDrawableChildMgr;

/**
 * Created by jmarranz on 10/11/14.
 */
public abstract class ClassDescElementDrawableChildNormal<TelementDrawable extends ElementDrawableChildNormal> extends ClassDescElementDrawableChild<ElementDrawableChildBridge>
{
    public ClassDescElementDrawableChildNormal(ClassDescDrawableOrElementDrawableChildMgr classMgr,String elemName)
    {
        super(classMgr,elemName);
    }
}
