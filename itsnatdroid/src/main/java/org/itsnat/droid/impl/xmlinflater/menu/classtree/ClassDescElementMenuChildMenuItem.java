package org.itsnat.droid.impl.xmlinflater.menu.classtree;

import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.GridLayoutAnimationController;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildBased;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildGroup;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildMenu;
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
import org.itsnat.droid.impl.xmlinflater.shared.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameMultiple;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescElementMenuChildMenuItem extends ClassDescElementMenuChildBased<ElementMenuChildMenuItem>
{
    public ClassDescElementMenuChildMenuItem(ClassDescMenuMgr classMgr)
    {
        super(classMgr,"item",null);
    }

    public boolean isAttributeIgnored(ElementMenuChildMenuItem resource, String namespaceURI, String name)
    {
        return super.isAttributeIgnored(resource,namespaceURI,name);
    }

    @Override
    public Class<ElementMenuChildMenuItem> getMenuOrElementMenuClass()
    {
        return ElementMenuChildMenuItem.class;
    }

    @Override
    public ElementMenuChildMenuItem createElementMenuChildBased(DOMElement domElement, DOMElement domElementParent, ElementMenuChildBased parentChildMenu, AttrMenuContext attrCtx)
    {
        return new ElementMenuChildMenuItem(parentChildMenu,domElement,attrCtx);
    }


    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescMenu_MenuItem_title(this));
        addAttrDescAN(new AttrDescMenu_MenuItem_titleCondensed(this));
        addAttrDescAN(new AttrDescMenu_MenuItem_icon(this));
        addAttrDescAN(new AttrDescMenu_MenuItem_showAsAction(this));
        addAttrDescAN(new AttrDescMenu_MenuItem_alphabeticShortcut(this));
        addAttrDescAN(new AttrDescMenu_MenuItem_numericShortcut(this));
        addAttrDescAN(new AttrDescMenu_MenuItem_checkable(this));
        addAttrDescAN(new AttrDescMenu_MenuItem_checked(this));
        addAttrDescAN(new AttrDescMenu_MenuItem_visible(this));


    }

}
