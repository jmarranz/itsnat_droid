package org.itsnat.droid.impl.xmlinflated.menu;

import android.view.Menu;
import android.view.MenuItem;

import org.itsnat.droid.ItsNatDroidException;

/**
 * Created by jmarranz on 30/11/14.
 */
public class ElementMenuChildMenuItem extends ElementMenuChildNormal
{
    protected MenuItem menuItem;

    public ElementMenuChildMenuItem(ElementMenuChildBased parentElementMenu)
    {
        super(parentElementMenu);
        Menu parentMenu;
        if (parentElementMenu instanceof ElementMenuChildSubMenu)
        {
            parentMenu = ((ElementMenuChildSubMenu) parentElementMenu).getSubMenu();
        }
        else if (parentElementMenu instanceof ElementMenuChildRoot)
        {
            parentMenu = ((ElementMenuChildRoot) parentElementMenu).getMenu();
        }
        else throw new ItsNatDroidException("Bad XML Menu");

        this.menuItem = parentMenu.add("");
    }

    public MenuItem getMenuItem()
    {
        return menuItem;
    }
}
