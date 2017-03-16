package org.itsnat.droid.impl.xmlinflater.menu.classtree;

import org.itsnat.droid.impl.dom.menu.DOMElemMenu;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChild;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildBase;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildMenuItem;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;
import org.itsnat.droid.impl.xmlinflater.menu.ClassDescMenuMgr;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescElementMenuChildMenuItem extends ClassDescElementMenuChildNormal<ElementMenuChildMenuItem>
{
    public ClassDescElementMenuChildMenuItem(ClassDescMenuMgr classMgr)
    {
        super(classMgr,"shape:corners",null);
    }

    @Override
    public Class<ElementMenuChildMenuItem> getMenuOrElementMenuClass()
    {
        return ElementMenuChildMenuItem.class;
    }

    @Override
    public ElementMenuChild createElementMenuChild(DOMElemMenu domElement, DOMElemMenu domElementParent, ElementMenuChildBase parentChildMenu, AttrMenuContext attrCtx)
    {
        return new ElementMenuChildMenuItem(parentChildMenu);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

/*
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "radius", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "topLeftRadius", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "topRightRadius", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "bottomRightRadius", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "bottomLeftRadius", 0f));
*/
    }

}
