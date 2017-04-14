package org.itsnat.droid.impl.xmlinflated.menu;


import android.view.Menu;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;

/**
 * Created by jmarranz on 30/11/14.
 */
public abstract class ElementMenuChildNormal extends ElementMenuChildBased
{
    public static final MapSmart<String,Integer> menuCategoryMap = MapSmart.<String,Integer>create(5);
    static
    {
        menuCategoryMap.put("container", Menu.CATEGORY_CONTAINER);
        menuCategoryMap.put("system", Menu.CATEGORY_SYSTEM);
        menuCategoryMap.put("never", Menu.NONE);
        menuCategoryMap.put("secondary", Menu.CATEGORY_SECONDARY);
        menuCategoryMap.put("alternative", Menu.CATEGORY_ALTERNATIVE);
    }

    protected int itemId = Menu.NONE;
    protected int menuCategory = Menu.CATEGORY_CONTAINER;
    //protected int orderInCategory;

    public ElementMenuChildNormal(ElementMenuChildBased parentElementMenu, DOMElement domElement, AttrMenuContext attrCtx)
    {
        super(parentElementMenu);

        XMLInflaterRegistry xmlInflaterRegistry = attrCtx.getXMLInflaterMenu().getXMLInflaterContext().getXMLInflaterRegistry();

        DOMAttr attrId = domElement.getDOMAttributeMap().getDOMAttribute(NamespaceUtil.XMLNS_ANDROID,"id");
        int itemId = attrId != null ? xmlInflaterRegistry.getIdentifier(attrId.getResourceDesc(), attrCtx.getXMLInflaterContext()) : Menu.NONE;
        if (itemId != Menu.NONE)
            this.itemId = itemId;
        else
            this.itemId = Menu.NONE;

        DOMAttr attrMenuCategory = domElement.getDOMAttributeMap().getDOMAttribute(NamespaceUtil.XMLNS_ANDROID,"menuCategory");
        String menuCategoryStr = attrMenuCategory != null ? xmlInflaterRegistry.getString(attrMenuCategory.getResourceDesc(), attrCtx.getXMLInflaterContext()) : null;
        if (menuCategoryStr != null)
            this.menuCategory = menuCategoryMap.get(menuCategoryStr);
        else
            this.menuCategory = Menu.CATEGORY_CONTAINER;

/*
        DOMAttr attrOrderInCategory = domElement.getDOMAttributeMap().getDOMAttribute(NamespaceUtil.XMLNS_ANDROID,"orderInCategory");
        int orderInCategory = attrOrderInCategory != null ? xmlInflaterRegistry.getIdentifier(attrId.getResourceDesc(), attrCtx.getXMLInflaterContext()) : Menu.NONE;
        if (orderInCategory != 0)
            this.orderInCategory = orderInCategory;
        else
            this.orderInCategory = Menu.NONE;
*/
    // orderInCategory no hay forma de meterlo en los menus, no hay método alguno
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
