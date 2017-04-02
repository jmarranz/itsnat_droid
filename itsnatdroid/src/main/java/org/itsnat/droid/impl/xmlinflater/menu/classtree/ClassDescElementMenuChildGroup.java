package org.itsnat.droid.impl.xmlinflater.menu.classtree;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.menu.DOMElemMenu;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildBased;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildGroup;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;
import org.itsnat.droid.impl.xmlinflater.menu.ClassDescMenuMgr;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescElementMenuChildGroup extends ClassDescElementMenuChildBased<ElementMenuChildGroup>
{
    public ClassDescElementMenuChildGroup(ClassDescMenuMgr classMgr)
    {
        super(classMgr,"group",null);
    }

    @Override
    public Class<ElementMenuChildGroup> getMenuOrElementMenuClass()
    {
        return ElementMenuChildGroup.class;
    }


    @Override
    public ElementMenuChildBased createElementMenuChildBased(DOMElement domElement, DOMElement domElementParent, ElementMenuChildBased parentChildMenu, AttrMenuContext attrCtx)
    {
        return new ElementMenuChildGroup(parentChildMenu);
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
