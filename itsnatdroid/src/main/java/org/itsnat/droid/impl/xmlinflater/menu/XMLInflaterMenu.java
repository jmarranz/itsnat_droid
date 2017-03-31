package org.itsnat.droid.impl.xmlinflater.menu;


import android.view.Menu;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.menu.DOMElemMenu;
import org.itsnat.droid.impl.dom.menu.XMLDOMMenu;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildBased;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildRoot;
import org.itsnat.droid.impl.xmlinflated.menu.InflatedXMLMenu;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterResource;
import org.itsnat.droid.impl.xmlinflater.menu.classtree.ClassDescElementMenuChildBased;
import org.itsnat.droid.impl.xmlinflater.menu.classtree.ClassDescElementMenuChildRoot;

import java.util.List;

/**
 * Created by jmarranz on 4/11/14.
 */
public class XMLInflaterMenu extends XMLInflaterResource<Menu>
{
    protected XMLInflaterMenu(InflatedXMLMenu inflatedXML, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        super(inflatedXML,bitmapDensityReference,attrResourceInflaterListener);
    }

    public static XMLInflaterMenu createXMLInflaterMenu(InflatedXMLMenu inflatedMenu, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        return new XMLInflaterMenu(inflatedMenu,bitmapDensityReference,attrResourceInflaterListener);
    }


    public InflatedXMLMenu getInflatedXMLMenu()
    {
        return (InflatedXMLMenu)inflatedXML;
    }

    public Menu inflateMenu(Menu androidRootMenu)
    {
        AttrMenuContext attrCtx = new AttrMenuContext(this);
        return inflateElementMenuChildRoot(getInflatedXMLMenu().getXMLDOMMenu(),attrCtx,androidRootMenu).getMenu();
    }

    private ElementMenuChildRoot inflateElementMenuChildRoot(XMLDOMMenu xmlDOMMenu, AttrMenuContext attrCtx, Menu androidRootMenu)
    {
        DOMElemMenu rootDOMElem = (DOMElemMenu)xmlDOMMenu.getRootDOMElement();
        return createElementMenuChildRootAndFillAttributes(rootDOMElem,attrCtx,androidRootMenu);
    }


    private ElementMenuChildRoot createElementMenuChildRootAndFillAttributes(DOMElemMenu rootDOMElem, AttrMenuContext attrCtx, Menu androidRootMenu)
    {
        InflatedXMLMenu inflatedMenu = getInflatedXMLMenu();

        String name = rootDOMElem.getTagName();
        if (!"menu".equals(name))
            throw new ItsNatDroidException("Expected <menu>, found " + name);
        ClassDescMenuMgr classDescMenuMgr = inflatedMenu.getXMLInflaterRegistry().getClassDescMenuMgr();
        ClassDescElementMenuChildRoot classDesc = (ClassDescElementMenuChildRoot)classDescMenuMgr.get("builtin-menu");
        if (classDesc == null)
            throw MiscUtil.internalError();
        ElementMenuChildRoot elemMenuChildRoot = createElementMenuChildRoot(classDesc, rootDOMElem,attrCtx,androidRootMenu);
        Menu menu = elemMenuChildRoot.getMenu();

        inflatedMenu.setMenu(menu);

        fillAttributes(classDesc, menu, rootDOMElem,attrCtx); // Por si acaso pero no tiene atributos

        return elemMenuChildRoot;
    }

    private ElementMenuChildRoot createElementMenuChildRoot(ClassDescElementMenuChildRoot classDesc, DOMElemMenu rootDOMElem, AttrMenuContext attrCtx, Menu androidRootMenu)
    {
        return classDesc.createElementMenuChildRoot(rootDOMElem,attrCtx,androidRootMenu);
    }

    @SuppressWarnings("unchecked")
    private void fillAttributes(ClassDescElementMenuChildBased classDesc, Menu menu, DOMElemMenu domElement, AttrMenuContext attrCtx)
    {
        classDesc.fillResourceAttributes(menu, domElement, attrCtx);
    }


    protected ElementMenuChildBased inflateNextElement(DOMElemMenu domElement, DOMElemMenu domElementParent, ElementMenuChildBased parentChildMenu, AttrMenuContext attrCtx)
    {
        ElementMenuChildBased childMenu = createElementMenuChildAndFillAttributes(domElement, domElementParent, parentChildMenu,attrCtx);

        processChildElements(domElement,childMenu,attrCtx);

        return childMenu;
    }

    private void getFullName(DOMElemMenu domElement,StringBuilder name)
    {
        if (domElement.getParentDOMElement() != null)
        {
            getFullName((DOMElemMenu)domElement.getParentDOMElement(),name);
            name.append(':');
        }
        name.append(domElement.getTagName());
    }

    private String getFullName(DOMElemMenu domElement)
    {
        StringBuilder name = new StringBuilder();
        getFullName(domElement,name);
        return name.toString();
    }

    private ElementMenuChildBased createElementMenuChildAndFillAttributes(DOMElemMenu domElement, DOMElemMenu domElementParent, ElementMenuChildBased parentChildMenu, AttrMenuContext attrCtx)
    {
        String parentName = getFullName(domElementParent);
        String name = parentName + ":" + domElement.getTagName();
        ClassDescMenuMgr classDescMenuMgr = getInflatedXMLMenu().getXMLInflaterRegistry().getClassDescMenuMgr();
        ClassDescElementMenuChildBased classDesc = (ClassDescElementMenuChildBased)classDescMenuMgr.get(name);
        if (classDesc == null)
        {
            throw new ItsNatDroidException("Unexpected error");
        }

        ElementMenuChildBased menuChild = createElementMenuChildBased(classDesc, domElement, domElementParent, parentChildMenu,attrCtx);

        fillAttributes(classDesc,menuChild , domElement, attrCtx); // ElementMenuChildContainer.create(menuChild)

        return menuChild;
    }


    private ElementMenuChildBased createElementMenuChildBased(ClassDescElementMenuChildBased classDesc, DOMElemMenu domElement, DOMElemMenu domElementParent, ElementMenuChildBased parentChildMenu, AttrMenuContext attrCtx)
    {
        return classDesc.createElementMenuChildBased(domElement, domElementParent, parentChildMenu,attrCtx);
    }

    @SuppressWarnings("unchecked")
    private void fillAttributes(ClassDescElementMenuChildBased classDesc, ElementMenuChildBased menuChild, DOMElemMenu domElement, AttrMenuContext attrCtx)
    {
        classDesc.fillResourceAttributes(menuChild, domElement, attrCtx);
    }

    public void processChildElements(DOMElemMenu domElemParent, ElementMenuChildBased parentChildMenu, AttrMenuContext attrCtx)
    {
        List<DOMElement> childDOMElemList = domElemParent.getChildDOMElementList();
        if (childDOMElemList == null) return;

        parentChildMenu.initElementMenuChildList(childDOMElemList.size());
        for (DOMElement childDOMElem : childDOMElemList)
        {
            ElementMenuChildBased childMenu = inflateNextElement((DOMElemMenu)childDOMElem,domElemParent,parentChildMenu,attrCtx);
            parentChildMenu.addElementMenuChild(childMenu);
        }
    }
}
