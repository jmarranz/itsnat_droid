package org.itsnat.droid.impl.xmlinflated.menu;

import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;


/**
 * Created by jmarranz on 30/11/14.
 */
public class ElementMenuChildSubMenu extends ElementMenuChildMenu
{
    protected SubMenu subMenu;
    protected int groupId;

    public ElementMenuChildSubMenu(ElementMenuChildMenuItem parentElementMenu)
    {
        super(parentElementMenu);

        // Los <menu> tipo SubMenu están debajo siempre de un <item> que a su vez está debajo del <menu> root, el <menu> es sólo un placeholder para indicar que hay un submenu

        //int parentItemId = parentElementMenu.getMenuItem().getItemId();

        ElementMenuChildRoot elemMenuChildRoot = getParentElementMenuChildRoot(parentElementMenu); // parentElementMenu es inicialmente un <item> pero necesitamos el <menu> root para crear el SubMenu
        int groupId = elemMenuChildRoot.startGroup();
        Menu parentRootMenu = elemMenuChildRoot.getMenu();

        ElementMenuChildMenuItem parentItemMenu = (ElementMenuChildMenuItem)parentElementMenu;
        MenuItem fromItem = parentItemMenu.getMenuItem();

        this.subMenu = parentRootMenu.addSubMenu(groupId,fromItem.getItemId(),Menu.NONE,"");
        MenuItem toItem = subMenu.getItem();

        copyItem(fromItem,toItem);

        // Ahora toca eleminar el <item> original nuestro, addSubMenu crea un <item> nuevo padre aunque no se refleje en el XML, por eso hemos hecho el copyItem, para poder
        // desacernos del <item> padre original nuestro, el cual no está realmente asociado al addSubMenu, de otra manera habrá una duplicidad de <item> padre aunque el
        // submenu sólo se aplique al segundo. Pero no lo hacemos aquí, lo hacemos en ElementMenuChildMenuItem en uno de los casos
    }


    private static void copyItem(MenuItem fromItem, MenuItem toItem) // http://grepcode.com/file/repo1.maven.org/maven2/org.robolectric/android-all/4.4_r1-robolectric-0/android/view/MenuInflater.java
    {
        // No hay setItemId pero no importa porque se pone el valor de la creación de addSubMenu
        toItem.setTitle(fromItem.getTitle());
        toItem.setChecked(fromItem.isChecked());
        toItem.setVisible(fromItem.isVisible());
        toItem.setEnabled(fromItem.isEnabled());
        toItem.setCheckable(fromItem.isCheckable());
        toItem.setTitleCondensed(fromItem.getTitleCondensed());
        toItem.setIcon(fromItem.getIcon());
        toItem.setAlphabeticShortcut(fromItem.getAlphabeticShortcut());
        toItem.setNumericShortcut(fromItem.getNumericShortcut());
    }


    @Override
    protected Menu getMenu()
    {
        return getSubMenu();
    }

    protected SubMenu getSubMenu()
    {
        return subMenu;
    }

    public int getGroupId()
    {
        return groupId;
    }
}
