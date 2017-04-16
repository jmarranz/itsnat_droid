package org.itsnat.droid.impl.xmlinflater.menu.classtree;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.menu.DOMElemMenu;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildBased;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildGroup;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildMenu;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;
import org.itsnat.droid.impl.xmlinflater.menu.ClassDescMenuMgr;



/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescElementMenuChildMenu extends ClassDescElementMenuChildBased<ElementMenuChildMenu>
{
    public ClassDescElementMenuChildMenu(ClassDescMenuMgr classMgr)
    {
        super(classMgr,"NONE",null);
    }

    @Override
    public Class<ElementMenuChildMenu> getMenuOrElementMenuClass()
    {
        return ElementMenuChildMenu.class;
    }

    public boolean isAttributeIgnored(ElementMenuChildMenu resource, String namespaceURI, String name)
    {
        return super.isAttributeIgnored(resource,namespaceURI,name);
    }

    @Override
    public ElementMenuChildBased createElementMenuChildBased(DOMElement domElement, DOMElement domElementParent, ElementMenuChildBased parentChildMenu, AttrMenuContext attrCtx)
    {
        return null; // new ElementMenuChildMenu(parentChildMenu);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();


    }

}
