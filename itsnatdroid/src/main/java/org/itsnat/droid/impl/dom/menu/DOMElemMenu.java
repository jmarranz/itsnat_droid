package org.itsnat.droid.impl.dom.menu;

import org.itsnat.droid.impl.dom.DOMElement;

/**
 * Created by jmarranz on 3/11/14.
 */
public class DOMElemMenu extends DOMElement
{
    public DOMElemMenu(DOMElement parentElement)
    {
        super("menu", parentElement);
    } // El padre de DOMElemMenu puede ser <item> o es el root (null)

}
