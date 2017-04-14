package org.itsnat.droid.impl.xmlinflater.menu.classtree;

import android.view.animation.AlphaAnimation;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.menu.DOMElemMenu;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildBased;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildGroup;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;
import org.itsnat.droid.impl.xmlinflater.menu.ClassDescMenuMgr;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescElementMenuChildGroup extends ClassDescElementMenuChildBased<ElementMenuChildGroup>
{
    public ClassDescElementMenuChildGroup(ClassDescMenuMgr classMgr)
    {
        super(classMgr,"group",null);
    }

    public boolean isAttributeIgnored(ElementMenuChildGroup resource, String namespaceURI, String name)
    {
        return NamespaceUtil.XMLNS_ITSNATDROID_RESOURCE.equals(namespaceURI) && name.equals("id") ||
                NamespaceUtil.XMLNS_ITSNATDROID_RESOURCE.equals(namespaceURI) && name.equals("menuCategory") ||
                NamespaceUtil.XMLNS_ITSNATDROID_RESOURCE.equals(namespaceURI) && name.equals("orderInCategory"); // Se usan especialmente en otra parte (ElementMenuChildMenuItem)
    }

    @Override
    public Class<ElementMenuChildGroup> getMenuOrElementMenuClass()
    {
        return ElementMenuChildGroup.class;
    }


    @Override
    public ElementMenuChildGroup createElementMenuChildBased(DOMElement domElement, DOMElement domElementParent, ElementMenuChildBased parentChildMenu, AttrMenuContext attrCtx)
    {
        return new ElementMenuChildGroup(parentChildMenu,domElement,attrCtx);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

    }

}
