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

        // Los <menu> tipo SubMenu están debajo siempre de un <item> que a su vez está debajo del <menu> root, el <menu> es sólo un placeholder para indicar que hay un submenu

        //int parentItemId = parentElementMenu.getMenuItem().getItemId();

        ElementMenuChildRoot elemMenuChildRoot = getParentElementMenuChildRoot(parentElementMenu); // parentElementMenu es inicialmente un <item> pero necesitamos el <menu> root para crear el SubMenu
        int groupId = elemMenuChildRoot.startGroup();
        Menu parentRootMenu = elemMenuChildRoot.getMenu();
        this.subMenu = parentRootMenu.addSubMenu(groupId,Menu.NONE,Menu.NONE,"");
    }


    @Override
    protected Menu getMenu()
    {
        return getSubMenu();
    }

    protected SubMenu getSubMenu()
    {
        return subMenu;
    }
}
