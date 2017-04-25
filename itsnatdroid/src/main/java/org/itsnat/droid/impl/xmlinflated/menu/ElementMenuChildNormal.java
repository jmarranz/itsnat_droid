package org.itsnat.droid.impl.xmlinflated.menu;


import android.view.Menu;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;

/**
 * Created by jmarranz on 30/11/14.
 */
public abstract class ElementMenuChildNormal extends ElementMenuChildBased
{


    public ElementMenuChildNormal(ElementMenuChildBased parentElementMenu, DOMElement domElement, AttrMenuContext attrCtx)
    {
        super(parentElementMenu,domElement,attrCtx);
    }

    static public ElementMenuChildRoot getParentElementMenuChildRoot(ElementMenuChildBased parentElementMenu)
    {
        ElementMenuChildRoot parentRootMenu;

        if (parentElementMenu instanceof ElementMenuChildRoot)
        {
            parentRootMenu = ((ElementMenuChildRoot)parentElementMenu);
        }
        else
        {
            parentRootMenu = getParentElementMenuChildRoot(parentElementMenu.getParentElementMenuChildBase());
        }

        return parentRootMenu;
    }

    public Menu getParentNativeRootMenu(ElementMenuChildBased parentElementMenu)
    {
        return getParentElementMenuChildRoot(parentElementMenu).getMenu();
    }


}
