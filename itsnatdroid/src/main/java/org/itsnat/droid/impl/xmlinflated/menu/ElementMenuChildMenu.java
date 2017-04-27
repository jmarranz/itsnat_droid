package org.itsnat.droid.impl.xmlinflated.menu;

import android.view.Menu;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;

/**
 * Created by jmarranz on 30/11/14.
 */
public abstract class ElementMenuChildMenu extends ElementMenuChildNormal
{
    public ElementMenuChildMenu(ElementMenuChildBased parentElementMenu, DOMElement domElement, AttrMenuContext attrCtx)
    {
        super(parentElementMenu,domElement,attrCtx);
    }

    protected abstract Menu getMenu();
}
