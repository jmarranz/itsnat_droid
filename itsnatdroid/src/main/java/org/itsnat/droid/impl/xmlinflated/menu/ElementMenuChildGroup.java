package org.itsnat.droid.impl.xmlinflated.menu;

import android.view.Menu;

/**
 * Created by jmarranz on 30/11/14.
 */
public class ElementMenuChildGroup extends ElementMenuChildNormal
{
    protected int groupId;

    public ElementMenuChildGroup(ElementMenuChildBased parentElementMenu)
    {
        super(parentElementMenu);

        // No intentamos obtener un objeto nativo porque no existe, es un descriptor en XML pero invisible

        ElementMenuChildRoot elemMenuChildRoot = getParentElementMenuChildRoot(parentElementMenu); // parentElementMenu es inicialmente un <item> pero necesitamos el <menu> root para crear el SubMenu
        this.groupId = elemMenuChildRoot.startGroup(); // Compartido por los hijos de ElementMenuChildGroup
    }

    public int getGroupId()
    {
        return groupId;
    }

}
