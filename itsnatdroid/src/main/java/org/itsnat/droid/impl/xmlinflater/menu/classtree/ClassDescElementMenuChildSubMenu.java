package org.itsnat.droid.impl.xmlinflater.menu.classtree;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.menu.DOMElemMenu;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildBased;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildMenuItem;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildSubMenu;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;
import org.itsnat.droid.impl.xmlinflater.menu.ClassDescMenuMgr;


/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescElementMenuChildSubMenu extends ClassDescElementMenuChildBased<ElementMenuChildSubMenu>
{
    public ClassDescElementMenuChildSubMenu(ClassDescMenuMgr classMgr,ClassDescElementMenuChildBased<? super ElementMenuChildSubMenu> parentClass)
    {
        super(classMgr,"menu",parentClass);
    }

    @Override
    public Class<ElementMenuChildSubMenu> getMenuOrElementMenuClass()
    {
        return ElementMenuChildSubMenu.class;
    }


    @Override
    public ElementMenuChildBased createElementMenuChildBased(DOMElement domElement, DOMElement domElementParent, ElementMenuChildBased parentElementMenu, AttrMenuContext attrCtx)
    {
        return new ElementMenuChildSubMenu((ElementMenuChildMenuItem)parentElementMenu);
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
