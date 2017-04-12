package org.itsnat.droid.impl.xmlinflater.menu.attr;

import android.view.MenuItem;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildMenuItem;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;
import org.itsnat.droid.impl.xmlinflater.menu.classtree.ClassDescElementMenuChildMenuItem;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescMenu_MenuItem_numericShortcut extends AttrDesc<ClassDescElementMenuChildMenuItem,ElementMenuChildMenuItem,AttrMenuContext>
{
    public AttrDescMenu_MenuItem_numericShortcut(ClassDescElementMenuChildMenuItem parent)
    {
        super(parent,"numericShortcut");
    }

    @Override
    public void setAttribute(ElementMenuChildMenuItem menuItem, DOMAttr attr, AttrMenuContext attrCtx)
    {
        String numericShortcut = getString(attr.getResourceDesc(), attrCtx.getXMLInflaterContext());
        MenuItem menuItemNat = menuItem.getMenuItem();
        menuItemNat.setNumericShortcut(numericShortcut.charAt(0));
    }

    @Override
    public void removeAttribute(ElementMenuChildMenuItem menuItem, AttrMenuContext attrCtx)
    {
        MenuItem menuItemNat = menuItem.getMenuItem();
        menuItemNat.setNumericShortcut('\u0000');
    }
}
