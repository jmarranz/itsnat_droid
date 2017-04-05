package org.itsnat.droid.impl.xmlinflater.menu.classtree;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildBased;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildMenuItem;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;
import org.itsnat.droid.impl.xmlinflater.menu.ClassDescMenuMgr;
import org.itsnat.droid.impl.xmlinflater.menu.attr.AttrDescMenu_MenuItem_title;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescElementMenuChildMenuItem extends ClassDescElementMenuChildBased<ElementMenuChildMenuItem>
{
    public ClassDescElementMenuChildMenuItem(ClassDescMenuMgr classMgr)
    {
        super(classMgr,"item",null);
    }

    @Override
    public Class<ElementMenuChildMenuItem> getMenuOrElementMenuClass()
    {
        return ElementMenuChildMenuItem.class;
    }

    @Override
    public ElementMenuChildMenuItem createElementMenuChildBased(DOMElement domElement, DOMElement domElementParent, ElementMenuChildBased parentChildMenu, AttrMenuContext attrCtx)
    {
        return new ElementMenuChildMenuItem(parentChildMenu,domElement,attrCtx);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescMenu_MenuItem_title(this));

/*
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "radius", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "topLeftRadius", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "topRightRadius", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "bottomRightRadius", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "bottomLeftRadius", 0f));
*/
    }

}
