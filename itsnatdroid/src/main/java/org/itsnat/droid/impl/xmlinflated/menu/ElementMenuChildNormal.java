package org.itsnat.droid.impl.xmlinflated.menu;


import android.view.Menu;

/**
 * Created by jmarranz on 30/11/14.
 */
public abstract class ElementMenuChildNormal extends ElementMenuChildBased
{
    public ElementMenuChildNormal(ElementMenuChildBased parentElementMenu)
    {
        super(parentElementMenu);
    }

    public ElementMenuChildRoot getParentElementMenuChildRoot(ElementMenuChildBased parentElementMenu)
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
