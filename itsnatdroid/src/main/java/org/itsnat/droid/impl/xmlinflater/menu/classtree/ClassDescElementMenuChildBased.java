package org.itsnat.droid.impl.xmlinflater.menu.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.menu.DOMElemMenu;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildBased;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildMenuItem;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildRoot;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;
import org.itsnat.droid.impl.xmlinflater.menu.ClassDescMenuMgr;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDescResourceBased;

/**
 * Created by jmarranz on 27/11/14.
 */
public abstract class ClassDescElementMenuChildBased<TelementMenuChild extends ElementMenuChildBased> extends ClassDescResourceBased<TelementMenuChild,AttrMenuContext>  // extends ClassDescMenu<TelementMenu>
{
    public ClassDescElementMenuChildBased(ClassDescMenuMgr classMgr, String elemName, ClassDescElementMenuChildBased<? super TelementMenuChild> parentClass)
    {
        super(classMgr, elemName, parentClass);
    }

    protected TelementMenuChild createResourceNative(Context ctx)
    {
        return null; // No se usa
    }

    public abstract Class<TelementMenuChild> getMenuOrElementMenuClass();

    public Class<TelementMenuChild> getDeclaredClass()
    {
        return getMenuOrElementMenuClass();
    }

    public ClassDescMenuMgr getClassDescMenuMgr()
    {
        return (ClassDescMenuMgr) classMgr;
    }

    public abstract ElementMenuChildBased createElementMenuChildBased(DOMElement domElement, DOMElement domElementParent, ElementMenuChildBased elementChildMenu, ElementMenuChildBased parentChildMenu, AttrMenuContext attrCtx);
}
