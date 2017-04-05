package org.itsnat.droid.impl.xmlinflated.menu;

import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;

import java.util.ArrayList;

/**
 * Created by jmarranz on 30/11/14.
 */
public class ElementMenuChildMenuItem extends ElementMenuChildNormal
{
    protected MenuItem menuItem;


    public ElementMenuChildMenuItem(ElementMenuChildBased parentElementMenu,DOMElement domElement,AttrMenuContext attrCtx)
    {
        super(parentElementMenu);

        // parentElementMenu es siempre el <menu> root o un submenu o un <group>

        XMLInflaterRegistry xmlInflaterRegistry = attrCtx.getXMLInflaterMenu().getXMLInflaterContext().getXMLInflaterRegistry();
        DOMAttr attrId = domElement.getDOMAttributeMap().getDOMAttribute(NamespaceUtil.XMLNS_ANDROID,"id");
        int itemId = xmlInflaterRegistry.getIdentifier(attrId.getResourceDesc(), attrCtx.getXMLInflaterContext());

        if (parentElementMenu instanceof ElementMenuChildGroup)
        {
            ElementMenuChildRoot childMenuRoot = getParentElementMenuChildRoot(parentElementMenu);
            int groupId = childMenuRoot.getCurrentGroupId();
            Menu rootMenu = childMenuRoot.getMenu();
            this.menuItem = rootMenu.add(groupId,itemId,Menu.NONE,"");
        }
        else if (parentElementMenu instanceof ElementMenuChildSubMenu)
        {
            ElementMenuChildSubMenu childSubMenu = ((ElementMenuChildSubMenu)parentElementMenu);
            ElementMenuChildRoot childMenuRoot = getParentElementMenuChildRoot(parentElementMenu);
            int groupId = childMenuRoot.getCurrentGroupId();
            this.menuItem = childSubMenu.getSubMenu().add(groupId,itemId,Menu.NONE,"");
        }
        else if (parentElementMenu instanceof ElementMenuChildRoot)
        {
            ElementMenuChildRoot childMenuRoot = (ElementMenuChildRoot)parentElementMenu;
            this.menuItem = childMenuRoot.getMenu().add(Menu.NONE,itemId,Menu.NONE,"");
        }
    }


    public MenuItem getMenuItem()
    {
        return menuItem;
    }

}
