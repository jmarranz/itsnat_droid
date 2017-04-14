package org.itsnat.droid.impl.xmlinflated.menu;

import android.view.Menu;
import android.view.MenuItem;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
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

        ElementMenuChildRoot elemMenuChildRoot = getParentElementMenuChildRoot(parentElementMenu); // parentElementMenu es inicialmente un <item> pero necesitamos el <menu> root para crear el SubMenu
        this.groupId = elemMenuChildRoot.startGroup(); // Compartido por los hijos de ElementMenuChildGroup
    }

    public int getGroupId()
    {
        return groupId;
    }

}
