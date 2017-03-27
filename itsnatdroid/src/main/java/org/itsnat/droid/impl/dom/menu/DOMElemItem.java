package org.itsnat.droid.impl.dom.menu;

import org.itsnat.droid.impl.dom.DOMElement;

/**
 * Created by jmarranz on 3/11/14.
 */
public class DOMElemItem extends DOMElement
{
    public DOMElemItem(DOMElement parentElement)
    {
        super("item", parentElement);
    } // El padre de DOMElemItem puede ser <item> o <group> o <menu> etc es decir todos
}
