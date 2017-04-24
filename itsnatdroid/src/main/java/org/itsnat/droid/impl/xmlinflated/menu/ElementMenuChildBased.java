package org.itsnat.droid.impl.xmlinflated.menu;

import android.view.Menu;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;

import java.util.ArrayList;

/**
 * Created by jmarranz on 1/12/14.
 */
public abstract class ElementMenuChildBased
{
    protected ArrayList<ElementMenuChildBased> elementMenuChildList;
    protected ElementMenuChildBased parentElementMenuChild;

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
    protected boolean checked;
    protected boolean checkedExits;
    protected boolean checkeable;
    protected boolean checkeableExits;
    protected boolean enabled;
    protected boolean enabledExits;
    protected boolean visible;
    protected boolean visibleExits;
    protected int menuCategory = Menu.CATEGORY_CONTAINER;
    protected String title;

    protected ElementMenuChildBased(ElementMenuChildBased parentElementMenuChild, DOMElement domElement,AttrMenuContext attrCtx)
    {
        this.parentElementMenuChild = parentElementMenuChild;

        XMLInflaterRegistry xmlInflaterRegistry = attrCtx.getXMLInflaterMenu().getXMLInflaterContext().getXMLInflaterRegistry();

        DOMAttr attrId = domElement.getDOMAttributeMap().getDOMAttribute(NamespaceUtil.XMLNS_ANDROID,"id");
        int itemId = attrId != null ? xmlInflaterRegistry.getIdentifier(attrId.getResourceDesc(), attrCtx.getXMLInflaterContext()) : Menu.NONE;
        if (itemId != Menu.NONE)
            this.itemId = itemId;
        else
            this.itemId = Menu.NONE;

        DOMAttr attrChecked = domElement.getDOMAttributeMap().getDOMAttribute(NamespaceUtil.XMLNS_ANDROID,"checked");
        if (attrChecked == null)
        {
            this.checked = false;
            this.checkedExits = false;
        }
        else
        {
            this.checked = xmlInflaterRegistry.getBoolean(attrChecked.getResourceDesc(), attrCtx.getXMLInflaterContext());
            this.checkedExits = true;
        }

        DOMAttr attrCheckeable = domElement.getDOMAttributeMap().getDOMAttribute(NamespaceUtil.XMLNS_ANDROID,"checkeable");
        if (attrCheckeable == null)
        {
            this.checkeable = false;
            this.checkeableExits = false;
        }
        else
        {
            this.checkeable = xmlInflaterRegistry.getBoolean(attrChecked.getResourceDesc(), attrCtx.getXMLInflaterContext());
            this.checkeableExits = true;
        }

        DOMAttr attrEnabled = domElement.getDOMAttributeMap().getDOMAttribute(NamespaceUtil.XMLNS_ANDROID,"enabled");
        if (attrEnabled == null)
        {
            this.enabled = true; // valor por defecto
            this.enabledExits = false;
        }
        else
        {
            this.enabled = xmlInflaterRegistry.getBoolean(attrChecked.getResourceDesc(), attrCtx.getXMLInflaterContext());
            this.enabledExits = true;
        }

        DOMAttr attrVisible = domElement.getDOMAttributeMap().getDOMAttribute(NamespaceUtil.XMLNS_ANDROID,"visible");
        if (attrVisible == null)
        {
            this.visible = true; // valor por defecto
            this.visibleExits = false;
        }
        else
        {
            this.visible = xmlInflaterRegistry.getBoolean(attrChecked.getResourceDesc(), attrCtx.getXMLInflaterContext());
            this.visibleExits = true;
        }

        DOMAttr attrMenuCategory = domElement.getDOMAttributeMap().getDOMAttribute(NamespaceUtil.XMLNS_ANDROID,"menuCategory");
        String menuCategoryStr = attrMenuCategory != null ? xmlInflaterRegistry.getString(attrMenuCategory.getResourceDesc(), attrCtx.getXMLInflaterContext()) : null;
        if (menuCategoryStr != null)
            this.menuCategory = menuCategoryMap.get(menuCategoryStr);
        else
            this.menuCategory = Menu.CATEGORY_CONTAINER;

        DOMAttr attrTitle = domElement.getDOMAttributeMap().getDOMAttribute(NamespaceUtil.XMLNS_ANDROID,"title");
        String attrTitleStr = attrTitle != null ? xmlInflaterRegistry.getString(attrTitle.getResourceDesc(), attrCtx.getXMLInflaterContext()) : null;
        if (attrTitleStr != null)
            this.title = attrTitleStr;
        else
            this.title = "";

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

    public ArrayList<ElementMenuChildBased> getElementMenuChildList()
    {
        return elementMenuChildList;
    }

    public void initElementMenuChildList(int size)
    {
        this.elementMenuChildList = new ArrayList<ElementMenuChildBased>(size);
    }

    public void addElementMenuChild(ElementMenuChildBased child)
    {
        elementMenuChildList.add(child);
    }


    public ElementMenuChildBased getParentElementMenuChildBase()
    {
        return parentElementMenuChild;
    }


    public ElementMenuChildRoot getElementMenuChildRoot()
    {
        return parentElementMenuChild != null ?  parentElementMenuChild.getElementMenuChildRoot() : null;
    }





}
