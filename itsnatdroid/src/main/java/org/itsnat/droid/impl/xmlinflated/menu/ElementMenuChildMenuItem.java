package org.itsnat.droid.impl.xmlinflated.menu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;

import java.util.ArrayList;
import java.util.List;

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
        int itemId = attrId != null ? xmlInflaterRegistry.getIdentifier(attrId.getResourceDesc(), attrCtx.getXMLInflaterContext()) : Menu.NONE;

        if (parentElementMenu instanceof ElementMenuChildGroup)
        {
            ElementMenuChildRoot childMenuRoot = getParentElementMenuChildRoot(parentElementMenu);
            Menu rootMenu = childMenuRoot.getMenu();
            int groupId = ((ElementMenuChildGroup)parentElementMenu).getGroupId();
            this.menuItem = rootMenu.add(groupId,itemId,Menu.NONE,"");
            return;
        }

        if (parentElementMenu instanceof ElementMenuChildSubMenu)
        {
            // Hijo de SubMenu
            ElementMenuChildSubMenu childSubMenu = ((ElementMenuChildSubMenu)parentElementMenu);
            int groupId = childSubMenu.getGroupId();
            this.menuItem = childSubMenu.getSubMenu().add(groupId,itemId,Menu.NONE,"");
            return;
        }

        {
            // Vemos si es padre "visual" de un SubMenu, este padre visual es nuestro y queda pero no integrado en el SubMenu que crea el suyo por lo que al final
            // se duplican, el primero el nuestro no funcional y el segundo el de el SubMenu si es funcional
            List<DOMElement> childList =  domElement.getChildDOMElementList();
            if (childList != null && !childList.isEmpty())
            {
                int size = childList.size();
                DOMElement child = childList.get(size - 1);
                if (child.getTagName().equals("menu"))
                {
                    // Hay un elemento hijo que es el <menu> esperado para un SubMenu, como este <item> tiene que desaparecer no hay que renderizarlo uniendo al árbol nativo, creamos un item via add
                    // pero lo eliminamos enseguida
                    if (itemId == 0) throw new ItsNatDroidException("id cannot be zero in this coontext");
                    ElementMenuChildRoot childMenuRoot = (ElementMenuChildRoot) parentElementMenu;
                    this.menuItem = childMenuRoot.getMenu().add(Menu.NONE, itemId, Menu.NONE, "");

                    childMenuRoot.getMenu().removeItem(itemId);
                    return;
                }
            }
        }

        if (parentElementMenu instanceof ElementMenuChildRoot)
        {
            // Resto de casos que no son <group> ni SubMenu, debe ser el último
            ElementMenuChildRoot childMenuRoot = (ElementMenuChildRoot) parentElementMenu;
            this.menuItem = childMenuRoot.getMenu().add(Menu.NONE, itemId, Menu.NONE, "");
            return;
        }
    }


    public MenuItem getMenuItem()
    {
        return menuItem;
    }

}
