package org.itsnat.droid.impl.xmlinflated.menu;

import android.view.Menu;
import android.view.SubMenu;

/**
 * Created by jmarranz on 30/11/14.
 */
public class ElementMenuChildSubMenu extends ElementMenuChildMenu
{
    protected SubMenu subMenu;

    public ElementMenuChildSubMenu(ElementMenuChildMenuItem parentElementMenu)
    {
        super(parentElementMenu);
    }

    protected Menu getSubMenu()
    {
        return subMenu;
    }
}
