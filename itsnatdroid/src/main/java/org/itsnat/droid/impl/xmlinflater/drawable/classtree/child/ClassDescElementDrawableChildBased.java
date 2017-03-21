package org.itsnat.droid.impl.xmlinflater.drawable.classtree.child;

import android.content.Context;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBase;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDescResourceBased;

/**
 * Created by jmarranz on 27/11/14.
 */
public abstract class ClassDescElementDrawableChildBased<TelementDrawableChild extends ElementDrawableChild> extends ClassDescResourceBased<TelementDrawableChild,AttrDrawableContext>  // extends ClassDescDrawable<TelementDrawable>
{
    public ClassDescElementDrawableChildBased(ClassDescDrawableMgr classMgr, String elemName, ClassDescElementDrawableChildBased<? super TelementDrawableChild> parentClass)
    {
        super(classMgr, elemName, parentClass);
    }

    protected TelementDrawableChild createResourceNative(Context ctx)
    {
        return null; // No se utiliza
    }

    public abstract Class<TelementDrawableChild> getDrawableOrElementDrawableClass();

    public Class<TelementDrawableChild> getDeclaredClass()
    {
        return getDrawableOrElementDrawableClass();
    }

    public ClassDescDrawableMgr getClassDescDrawableMgr()
    {
        return (ClassDescDrawableMgr) classMgr;
    }

    public abstract ElementDrawableChild createElementDrawableChild(DOMElemDrawable domElement, DOMElemDrawable domElementParent, ElementDrawableChildBase parentChildDrawable, AttrDrawableContext attrCtx);
}
