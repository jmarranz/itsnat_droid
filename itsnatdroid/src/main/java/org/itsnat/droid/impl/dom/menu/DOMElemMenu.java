package org.itsnat.droid.impl.dom.menu;

import org.itsnat.droid.impl.dom.DOMElement;

/**
 * Created by jmarranz on 3/11/14.
 */
public class DOMElemMenu extends DOMElement
{
    protected boolean submenu = false;

    public DOMElemMenu(boolean submenu,DOMElement parentElement)
    {
        super("menu", parentElement);
        this.submenu = submenu;
    } // El padre de DOMElemMenu puede ser <item> o es el root (null)

    public boolean isSubMenu() { return submenu; }

}
