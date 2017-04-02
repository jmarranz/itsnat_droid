package org.itsnat.droid.impl.xmlinflated.menu;

import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import org.itsnat.droid.ItsNatDroidException;

/**
 * Created by jmarranz on 30/11/14.
 */
public class ElementMenuChildSubMenu extends ElementMenuChildMenu
{
    protected SubMenu subMenu;

    public ElementMenuChildSubMenu(ElementMenuChildMenuItem parentElementMenu)
    {
        super(parentElementMenu);

        // MenuItem menuItem = parentElementMenu.getMenuItem(); // Los <menu> tipo SubMenu están debajo siempre de un <item> que a su vez está debajo dee un <menu> root

        Menu menu = getParentNativeMenu(parentElementMenu); // parentElementMenu es inicialmente un <item> pero necesitamos el un <menu>
        this.subMenu = menu.addSubMenu("");
    }

    public Menu getParentNativeMenu(ElementMenuChildBased parentElementMenu)
    {
        Menu parentMenu;

        if (parentElementMenu instanceof ElementMenuChildRoot)
        {
            parentMenu = ((ElementMenuChildRoot) parentElementMenu).getMenu();
        }
        else if (parentElementMenu instanceof ElementMenuChildMenuItem)
        {
            parentMenu = getParentNativeMenu(parentElementMenu.getParentElementMenuChildBase());
        }
        else throw new ItsNatDroidException("Bad XML Menu");

        return parentMenu;
    }

    protected Menu getMenu()
    {
        return getSubMenu();
    }

    protected SubMenu getSubMenu()
    {
        return subMenu;
    }
}
