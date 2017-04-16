package org.itsnat.droid.impl.xmlinflater.menu.classtree;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildBased;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildMenuItem;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;
import org.itsnat.droid.impl.xmlinflater.menu.ClassDescMenuMgr;
import org.itsnat.droid.impl.xmlinflater.menu.attr.AttrDescMenu_MenuItem_alphabeticShortcut;
import org.itsnat.droid.impl.xmlinflater.menu.attr.AttrDescMenu_MenuItem_checkable;
import org.itsnat.droid.impl.xmlinflater.menu.attr.AttrDescMenu_MenuItem_checked;
import org.itsnat.droid.impl.xmlinflater.menu.attr.AttrDescMenu_MenuItem_icon;
import org.itsnat.droid.impl.xmlinflater.menu.attr.AttrDescMenu_MenuItem_numericShortcut;
import org.itsnat.droid.impl.xmlinflater.menu.attr.AttrDescMenu_MenuItem_showAsAction;
import org.itsnat.droid.impl.xmlinflater.menu.attr.AttrDescMenu_MenuItem_title;
import org.itsnat.droid.impl.xmlinflater.menu.attr.AttrDescMenu_MenuItem_titleCondensed;
import org.itsnat.droid.impl.xmlinflater.menu.attr.AttrDescMenu_MenuItem_visible;

/**
 * Created by jmarranz on 10/11/14.
 */
public abstract class ClassDescElementMenuChildNormal extends ClassDescElementMenuChildBased<ElementMenuChildMenuItem>
{
    public ClassDescElementMenuChildNormal(ClassDescMenuMgr classMgr,String name)
    {
        super(classMgr,"name",null);
    }

    public boolean isAttributeIgnored(ElementMenuChildMenuItem resource, String namespaceURI, String name)
    {
        return NamespaceUtil.XMLNS_ITSNATDROID_RESOURCE.equals(namespaceURI) && name.equals("id") ||
                NamespaceUtil.XMLNS_ITSNATDROID_RESOURCE.equals(namespaceURI) && name.equals("menuCategory") ||
                NamespaceUtil.XMLNS_ITSNATDROID_RESOURCE.equals(namespaceURI) && name.equals("orderInCategory"); // Se usan especialmente en otra parte (ElementMenuChildMenuItem)
    }


    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

    }

}
