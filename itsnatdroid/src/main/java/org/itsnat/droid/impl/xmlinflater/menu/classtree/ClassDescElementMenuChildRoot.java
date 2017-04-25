package org.itsnat.droid.impl.xmlinflater.menu.classtree;

import android.view.Menu;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildBased;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildMenuItem;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildRoot;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;
import org.itsnat.droid.impl.xmlinflater.menu.ClassDescMenuMgr;


/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescElementMenuChildRoot extends ClassDescElementMenuChildBased<ElementMenuChildRoot>
{
    public ClassDescElementMenuChildRoot(ClassDescMenuMgr classMgr)
    {
        super(classMgr,"builtin-menu",null);
    }

    public boolean isAttributeIgnored(ElementMenuChildMenuItem resource, String namespaceURI, String name)
    {
        return true; // No hay aributos en <menu>
    }

    @Override
    public Class<ElementMenuChildRoot> getMenuOrElementMenuClass()
    {
        return ElementMenuChildRoot.class;
    }

    public ElementMenuChildBased createElementMenuChildRoot(DOMElement rootElem, AttrMenuContext attrCtx, Menu androidRootMenu)
    {
        return new ElementMenuChildRoot(null,rootElem, attrCtx, androidRootMenu); // Ponemos el androidRootMenu en vez de nuestro Menu root del XML, total no tienen atributos, de esta manera conseguimos conectar el menu predefinido con nuestro XML sin perder nada a base de añadir por abajo
    }

    @Override
    public ElementMenuChildBased createElementMenuChildBased(DOMElement domElement, DOMElement domElementParent, ElementMenuChildBased elementChildMenu,ElementMenuChildBased parentChildMenu, AttrMenuContext attrCtx)
    {
        return null;
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

    }

}
