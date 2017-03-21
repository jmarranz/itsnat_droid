package org.itsnat.droid.impl.xmlinflater.menu.classtree;

import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildMenuItem;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildNormal;
import org.itsnat.droid.impl.xmlinflater.menu.ClassDescMenuMgr;

/**
 * Created by jmarranz on 10/11/14.
 */
public abstract class ClassDescElementMenuChildNormal<TelementMenuChild extends ElementMenuChildNormal> extends ClassDescElementMenuChildBased<ElementMenuChildNormal>
{
    public ClassDescElementMenuChildNormal(ClassDescMenuMgr classMgr, String elemName, ClassDescElementMenuChildBased<? super ElementMenuChildNormal> parentClass)
    {
        super(classMgr,elemName,parentClass);
    }

}

