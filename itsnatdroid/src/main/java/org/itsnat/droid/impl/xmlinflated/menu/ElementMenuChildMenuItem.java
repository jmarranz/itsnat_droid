package org.itsnat.droid.impl.xmlinflated.menu;

import android.view.Menu;
import android.view.MenuItem;

import org.itsnat.droid.ItsNatDroidException;

import java.util.ArrayList;

/**
 * Created by jmarranz on 30/11/14.
 */
public class ElementMenuChildMenuItem extends ElementMenuChildNormal
{
    protected MenuItem menuItem;
    protected Menu parentMenu;


    public ElementMenuChildMenuItem(ElementMenuChildBased parentElementMenu)
    {
        super(parentElementMenu);

        // parentElementMenu es siempre el <menu> root o un submenu o un <group>

        Menu parentMenu = getParentNativeMenu(parentElementMenu);

        this.menuItem = parentMenu.add("");

        this.parentMenu = parentMenu;
    }


    public Menu getParentNativeMenu(ElementMenuChildBased parentElementMenu)
    {
        Menu parentMenu;

        if (parentElementMenu instanceof ElementMenuChildSubMenu)
        {
            parentMenu = ((ElementMenuChildSubMenu) parentElementMenu).getSubMenu();
        }
        else if (parentElementMenu instanceof ElementMenuChildRoot)
        {
            parentMenu = ((ElementMenuChildRoot) parentElementMenu).getMenu();
        }
        else if (parentElementMenu instanceof ElementMenuChildGroup)   // A día de hoy sólo conocemos <group/> bajo <menu> root o bajo un <menu> como submenu, se pueden poner dos <group> anidados pero el anidado yo creo que no hace nada
        {
            parentMenu = getParentNativeMenu(parentElementMenu.getParentElementMenuChildBase());
        }
        else throw new ItsNatDroidException("Bad XML Menu");

        return parentMenu;
    }


    public MenuItem getMenuItem()
    {
        return menuItem;
    }

    public Menu getParentMenu()
    {
        return parentMenu;
    }
}
