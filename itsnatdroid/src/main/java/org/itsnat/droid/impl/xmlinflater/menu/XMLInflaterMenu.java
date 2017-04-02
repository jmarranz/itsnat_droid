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
        if (androidRootMenu != menu) throw MiscUtil.internalError();
        inflatedMenu.setMenu(menu);

        fillAttributes(classDesc, menu, rootDOMElem,attrCtx); // Por si acaso pero no tiene atributos

        processChildElements(rootDOMElem, elemMenuChildRoot,attrCtx);

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


    protected ElementMenuChildBased inflateNextElement(DOMElement domElement, DOMElement domElementParent, ElementMenuChildBased parentChildMenu, AttrMenuContext attrCtx)
    {
        ElementMenuChildBased menuChild = createElementMenuChildAndFillAttributes(domElement, domElementParent, parentChildMenu,attrCtx);

        processChildElements(domElement,menuChild,attrCtx);

        return menuChild;
    }


    private ElementMenuChildBased createElementMenuChildAndFillAttributes(DOMElement domElement, DOMElement domElementParent, ElementMenuChildBased parentChildMenu, AttrMenuContext attrCtx)
    {
        String tagName = domElement.getTagName();
        ClassDescMenuMgr classDescMenuMgr = getInflatedXMLMenu().getXMLInflaterRegistry().getClassDescMenuMgr();
        ClassDescElementMenuChildBased classDesc = (ClassDescElementMenuChildBased)classDescMenuMgr.get(tagName);
        if (classDesc == null)
            throw new ItsNatDroidException("Unexpected error");

        ElementMenuChildBased menuChild = createElementMenuChildBased(classDesc, domElement, domElementParent, parentChildMenu,attrCtx);

        fillAttributes(classDesc,menuChild , domElement, attrCtx);

        return menuChild;
    }


    private ElementMenuChildBased createElementMenuChildBased(ClassDescElementMenuChildBased classDesc, DOMElement domElement, DOMElement domElementParent, ElementMenuChildBased parentChildMenu, AttrMenuContext attrCtx)
    {
        return classDesc.createElementMenuChildBased(domElement, domElementParent, parentChildMenu,attrCtx);
    }

    @SuppressWarnings("unchecked")
    private void fillAttributes(ClassDescElementMenuChildBased classDesc, ElementMenuChildBased menuChild, DOMElement domElement, AttrMenuContext attrCtx)
    {
        classDesc.fillResourceAttributes(menuChild, domElement, attrCtx);
    }

    public void processChildElements(DOMElement domElemParent, ElementMenuChildBased parentChildMenu, AttrMenuContext attrCtx)
    {
        List<DOMElement> childDOMElemList = domElemParent.getChildDOMElementList();
        if (childDOMElemList == null) return;

        parentChildMenu.initElementMenuChildList(childDOMElemList.size());
        for (DOMElement childDOMElem : childDOMElemList)
        {
            ElementMenuChildBased childMenu = inflateNextElement(childDOMElem,domElemParent,parentChildMenu,attrCtx);
            parentChildMenu.addElementMenuChild(childMenu);
        }
    }
}
