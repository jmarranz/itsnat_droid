package org.itsnat.droid.impl.xmlinflater.menu.classtree;

import android.view.Menu;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildBased;
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

    @Override
    public Class<ElementMenuChildRoot> getMenuOrElementMenuClass()
    {
        return ElementMenuChildRoot.class;
    }

    public ElementMenuChildRoot createElementMenuChildRoot(DOMElement rootElem, AttrMenuContext attrCtx, Menu androidRootMenu)
    {
        return new ElementMenuChildRoot(androidRootMenu); // Ponemos el androidRootMenu en vez de nuestro Menu root del XML, total no tienen atributos, de esta manera conseguimos conectar el menu predefinido con nuestro XML sin perder nada a base de añadir por abajo
    }

    @Override
    public ElementMenuChildBased createElementMenuChildBased(DOMElement domElement, DOMElement domElementParent, ElementMenuChildBased parentChildMenu, AttrMenuContext attrCtx)
    {
        return null;
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        /*
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "radius", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "topLeftRadius", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "topRightRadius", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "bottomRightRadius", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "bottomLeftRadius", 0f));
        */
    }

}
