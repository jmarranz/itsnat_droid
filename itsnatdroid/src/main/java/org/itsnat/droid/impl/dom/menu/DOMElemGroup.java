package org.itsnat.droid.impl.dom.menu;

import org.itsnat.droid.impl.dom.DOMElement;

/**
 * Created by jmarranz on 3/11/14.
 */
public class DOMElemGroup extends DOMElement
{
    public DOMElemGroup(DOMElement parentElement)
    {
        super("group", parentElement);
    } // El padre de DOMElemGroup puede ser <item> o <menu> etc es decir todos
}
