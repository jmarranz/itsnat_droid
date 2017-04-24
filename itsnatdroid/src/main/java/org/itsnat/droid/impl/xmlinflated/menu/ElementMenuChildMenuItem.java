package org.itsnat.droid.impl.xmlinflated.menu;

import android.view.Menu;
import android.view.MenuItem;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;

import java.util.List;

/**
 * Created by jmarranz on 30/11/14.
 */
public class ElementMenuChildMenuItem extends ElementMenuChildNormal
{
    protected MenuItem menuItem;


    public ElementMenuChildMenuItem(ElementMenuChildBased parentElementMenu,DOMElement domElement,AttrMenuContext attrCtx)
    {
        super(parentElementMenu,domElement,attrCtx);

        // parentElementMenu es siempre el <menu> root o un submenu o un <group>

        if (parentElementMenu instanceof ElementMenuChildGroup)
        {
            ElementMenuChildRoot childMenuRoot = getParentElementMenuChildRoot(parentElementMenu);
            Menu rootMenu = childMenuRoot.getMenu();
            int groupId = ((ElementMenuChildGroup)parentElementMenu).getGroupId();

            int menuCategory = this.menuCategory;
            if (menuCategory == Menu.NONE)
                menuCategory = ((ElementMenuChildGroup)parentElementMenu).menuCategory;

            if (!this.checkeableExits) // Boolean.FALSE, no local
                checkeable = ((ElementMenuChildGroup) parentElementMenu).checkeable;

            if (!this.checkedExits)
                checked = ((ElementMenuChildGroup)parentElementMenu).checked;

            if (!this.enabledExits)
                enabled = ((ElementMenuChildGroup)parentElementMenu).enabled;

            if (!this.visibleExits)
                visible = ((ElementMenuChildGroup)parentElementMenu).visible;

            this.menuItem = rootMenu.add(groupId,itemId,menuCategory,title); // Cojemos el idemId del item sea cual sea
            menuItem.setCheckable(checkeable);
            menuItem.setChecked(checked);
            menuItem.setEnabled(enabled);
            menuItem.setVisible(visible);

            return;
        }

        if (parentElementMenu instanceof ElementMenuChildRoot)
        {
            // Vemos si es padre "visual" de un SubMenu, este padre visual es nuestro y queda pero no integrado en el SubMenu que crea el suyo por lo que al final
            // se duplican, el primero el nuestro no funcional y el segundo el de el SubMenu si es funcional
            // Vemos si este <item> es padre de un SubMenu
            boolean isMenuItemParentOfSubMenu = false;
            List<DOMElement> childList = domElement.getChildDOMElementList();
            if (childList != null && !childList.isEmpty())
            {
                DOMElement child = childList.get(0);

                // Hijo de <item> tipo SubMenu
                if (child.getTagName().equals("menu"))
                {
                    // Hay un elemento hijo que es el <menu> esperado para un SubMenu, como este <item> tiene que desaparecer no hay que renderizarlo uniendo al árbol nativo, creamos un item via add
                    // pero lo eliminamos enseguida
                    isMenuItemParentOfSubMenu = true;
                }
                else isMenuItemParentOfSubMenu = false;
            }

            if (isMenuItemParentOfSubMenu)
            {
                ElementMenuChildRoot childMenuRoot = (ElementMenuChildRoot) parentElementMenu;
                int groupId = childMenuRoot.getCurrentGroupId();

                int menuCategory = this.menuCategory;
                if (menuCategory == Menu.NONE)
                    menuCategory = ((ElementMenuChildRoot) parentElementMenu).menuCategory;

                if (!this.checkeableExits) // Boolean.FALSE, no local
                    checkeable = ((ElementMenuChildRoot) parentElementMenu).checkeable;

                if (!this.checkedExits) checked = ((ElementMenuChildRoot) parentElementMenu).checked;

                if (!this.enabledExits) enabled = ((ElementMenuChildRoot) parentElementMenu).enabled;

                if (!this.visibleExits) visible = ((ElementMenuChildRoot) parentElementMenu).visible;

                this.menuItem = childMenuRoot.getMenu().add(groupId, itemId, menuCategory, title);
                menuItem.setCheckable(checkeable);
                menuItem.setChecked(checked);
                menuItem.setEnabled(enabled);
                menuItem.setVisible(visible);

                return;
            }
            else
            {
                ElementMenuChildRoot childMenuRoot = ((ElementMenuChildRoot)parentElementMenu);
                int groupId = childMenuRoot.getCurrentGroupId();

                int menuCategory = this.menuCategory;
                if (menuCategory == Menu.NONE)
                    menuCategory = ((ElementMenuChildRoot)parentElementMenu).menuCategory;

                if (!this.checkeableExits) // Boolean.FALSE, no local
                    checkeable = ((ElementMenuChildRoot) parentElementMenu).checkeable;

                if (!this.checkedExits)
                    checked = ((ElementMenuChildRoot)parentElementMenu).checked;

                if (!this.enabledExits)
                    enabled = ((ElementMenuChildRoot)parentElementMenu).enabled;

                if (!this.visibleExits)
                    visible = ((ElementMenuChildRoot)parentElementMenu).visible;

                this.menuItem = ((ElementMenuChildRoot)parentElementMenu).getMenu().add(groupId,itemId,menuCategory,title); // Cojemos el idemId del item sea cual sea
                menuItem.setCheckable(checkeable);
                menuItem.setChecked(checked);
                menuItem.setEnabled(enabled);
                menuItem.setVisible(visible);

                return;
            }
        }

    }


    public MenuItem getMenuItem()
    {
        return menuItem;
    }

}
