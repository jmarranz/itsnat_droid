package org.itsnat.droid.impl.xmlinflated.menu;

import android.view.Menu;
import android.view.SubMenu;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;

/**
 * Created by jmarranz on 30/11/14.
 */
public class ElementMenuChildGroup extends ElementMenuChildNormal
{
    protected int groupId;
    protected int menuCategory = Menu.CATEGORY_CONTAINER;

    public ElementMenuChildGroup(ElementMenuChildBased parentElementMenu,DOMElement domElement,AttrMenuContext attrCtx)
    {
        super(parentElementMenu,domElement,attrCtx);

        // No intentamos obtener un objeto nativo porque no existe, es un descriptor en XML pero invisible

        // http://stackoverflow.com/questions/35772383/androidcheckablebehavior-set-programmatically

        ElementMenuChildRoot elemMenuChildRoot = getParentElementMenuChildRoot(parentElementMenu); // parentElementMenu es inicialmente un <item> pero necesitamos el <menu> root para crear el SubMenu
        this.groupId = elemMenuChildRoot.startGroup(); // Compartido por los hijos de ElementMenuChildGroup



        // Los atributos android:checkableBehavior="...", android:visible, android:enable sólo fuoncionan en  XML nativo
    }

    public int getGroupId()
    {
        return groupId;
    }


}
