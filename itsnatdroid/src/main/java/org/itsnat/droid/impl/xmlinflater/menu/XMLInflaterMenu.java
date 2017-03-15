package org.itsnat.droid.impl.xmlinflater.menu;


import android.view.Menu;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.menu.DOMElemMenu;
import org.itsnat.droid.impl.dom.menu.XMLDOMMenu;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildNormal;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildBase;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildRoot;
import org.itsnat.droid.impl.xmlinflated.menu.InflatedXMLMenu;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterResource;

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

    public Menu inflateMenu()
    {
        AttrMenuContext attrCtx = new AttrMenuContext(this);
        return inflateElementMenuChildRoot(getInflatedXMLMenu().getXMLDOMMenu(),attrCtx).getMenu();
    }

    private ElementMenuChildRoot inflateElementMenuChildRoot(XMLDOMMenu xmlDOMMenu, AttrMenuContext attrCtx)
    {
        DOMElemMenu rootDOMElem = (DOMElemMenu)xmlDOMMenu.getRootDOMElement();
        return createElementMenuChildRootAndFillAttributes(rootDOMElem,attrCtx);
    }


    private ElementMenuChildRoot createElementMenuChildRootAndFillAttributes(DOMElemMenu rootDOMElem, AttrMenuContext attrCtx)
    {
        InflatedXMLMenu inflatedMenu = getInflatedXMLMenu();

        String name = rootDOMElem.getTagName();
        ClassDescMenuMgr classDescMenuMgr = inflatedMenu.getXMLInflaterRegistry().getClassDescMenuMgr();

        ClassDescElementMenuBased classDesc = (ClassDescElementMenuBased)classDescMenuMgr.get(name);
        if (classDesc == null)
            throw new ItsNatDroidException("Menu type is not supported: " + name);
        ElementMenuChildRoot menuElem = createElementMenuChildRoot(classDesc, rootDOMElem,attrCtx);
        Menu menu = menuElem.getMenu();

        inflatedMenu.setMenu(menu);

        fillAttributes(classDesc, menu, rootDOMElem,attrCtx);

        return menuElem;
    }

    private ElementMenuChildRoot createElementMenuChildRoot(ClassDescElementMenuBased classDesc, DOMElemMenu rootDOMElem, AttrMenuContext attrCtx)
    {
        return classDesc.createElementMenuChildRoot(rootDOMElem,attrCtx);
    }

    @SuppressWarnings("unchecked")
    private void fillAttributes(ClassDescElementMenuBased classDesc, Menu menu, DOMElemMenu domElement, AttrMenuContext attrCtx)
    {
        classDesc.fillResourceAttributes(menu, domElement, attrCtx);
    }

    /*
    @SuppressWarnings("unchecked")
    private void fillAttributes(ClassDescResourceBased classDesc, MenuOrElementMenuWrapper menu, DOMElemMenu domElement, AttrMenuContext attrCtx)
    {
        classDesc.fillResourceAttributes(menu, domElement, attrCtx);
    }
*/



    protected ElementMenuChildBase inflateNextElement(DOMElemMenu domElement, DOMElemMenu domElementParent, ElementMenuChildBase parentChildMenu, AttrMenuContext attrCtx)
    {
        ElementMenuChildNormal childMenu = createElementMenuChildAndFillAttributes(domElement, domElementParent, parentChildMenu,attrCtx);

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

    private ElementMenuChildNormal createElementMenuChildAndFillAttributes(DOMElemMenu domElement, DOMElemMenu domElementParent, ElementMenuChildBase parentChildMenu, AttrMenuContext attrCtx)
    {
        String parentName = getFullName(domElementParent);
        String name = parentName + ":" + domElement.getTagName();
        ClassDescMenuMgr classDescMenuMgr = getInflatedXMLMenu().getXMLInflaterRegistry().getClassDescMenuMgr();
        ClassDescElementMenuChildBased classDesc = (ClassDescElementMenuChildBased)classDescMenuMgr.get(name);
        if (classDesc == null)
        {
            name = ClassDescElementMenuChildMenuBridge.NAME; // "*";
            classDesc = (ClassDescElementMenuChildMenuBridge)classDescMenuMgr.get(name);
            if (classDesc == null) throw new ItsNatDroidException("Unexpected error"); // ClassDescElementMenuChildMenuBridge debe estar registrado previamente
        }

        ElementMenuChildNormal menuChild = createElementMenuChild(classDesc, domElement, domElementParent, parentChildMenu,attrCtx);

        fillAttributes(classDesc,menuChild , domElement, attrCtx); // ElementMenuChildContainer.create(menuChild)

        return menuChild;
    }


    private ElementMenuChildNormal createElementMenuChild(ClassDescElementMenuChildBased classDesc, DOMElemMenu domElement, DOMElemMenu domElementParent, ElementMenuChildBase parentChildMenu, AttrMenuContext attrCtx)
    {
        return classDesc.createElementMenuChild(domElement, domElementParent, parentChildMenu,attrCtx);
    }

    @SuppressWarnings("unchecked")
    private void fillAttributes(ClassDescElementMenuChildBased classDesc, ElementMenuChildNormal menuChild, DOMElemMenu domElement, AttrMenuContext attrCtx)
    {
        classDesc.fillResourceAttributes(menuChild, domElement, attrCtx);
    }

    public void processChildElements(DOMElemMenu domElemParent, ElementMenuChildBase parentChildMenu, AttrMenuContext attrCtx)
    {
        List<DOMElement> childDOMElemList = domElemParent.getChildDOMElementList();
        if (childDOMElemList == null) return;

        parentChildMenu.initElementMenuChildList(childDOMElemList.size());
        for (DOMElement childDOMElem : childDOMElemList)
        {
            ElementMenuChildBase childMenu = inflateNextElement((DOMElemMenu)childDOMElem,domElemParent,parentChildMenu,attrCtx);
            parentChildMenu.addElementMenuChild(childMenu);
        }
    }
}
