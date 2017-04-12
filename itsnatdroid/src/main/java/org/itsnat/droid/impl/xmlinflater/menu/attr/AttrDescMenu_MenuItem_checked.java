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
public class AttrDescMenu_MenuItem_checked extends AttrDesc<ClassDescElementMenuChildMenuItem,ElementMenuChildMenuItem,AttrMenuContext>
{
    public AttrDescMenu_MenuItem_checked(ClassDescElementMenuChildMenuItem parent)
    {
        super(parent,"checked");
    }

    @Override
    public void setAttribute(ElementMenuChildMenuItem menuItem, DOMAttr attr, AttrMenuContext attrCtx)
    {
        boolean checked = getBoolean(attr.getResourceDesc(), attrCtx.getXMLInflaterContext());
        MenuItem menuItemNat = menuItem.getMenuItem();
        menuItemNat.setChecked(checked);
    }

    @Override
    public void removeAttribute(ElementMenuChildMenuItem menuItem, AttrMenuContext attrCtx)
    {
        MenuItem menuItemNat = menuItem.getMenuItem();
        menuItemNat.setChecked(false);
    }
}
