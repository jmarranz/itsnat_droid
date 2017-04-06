package org.itsnat.droid.impl.xmlinflater.menu.attr;

import android.graphics.drawable.Drawable;
import android.view.MenuItem;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildMenuItem;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawable;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;
import org.itsnat.droid.impl.xmlinflater.menu.classtree.ClassDescElementMenuChildMenuItem;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescMenu_MenuItem_title extends AttrDesc<ClassDescElementMenuChildMenuItem,ElementMenuChildMenuItem,AttrMenuContext>
{
    public AttrDescMenu_MenuItem_title(ClassDescElementMenuChildMenuItem parent)
    {
        super(parent,"title");
    }

    @Override
    public void setAttribute(ElementMenuChildMenuItem menuItem, DOMAttr attr, AttrMenuContext attrCtx)
    {
        String title = getString(attr.getResourceDesc(), attrCtx.getXMLInflaterContext());
        MenuItem menuItemNat = menuItem.getMenuItem();
        menuItemNat.setTitle(title); // Hay un caso en el que no renderizamos y no creamos MenuItem native
    }

    @Override
    public void removeAttribute(ElementMenuChildMenuItem menuItem, AttrMenuContext attrCtx)
    {
        MenuItem menuItemNat = menuItem.getMenuItem();
        menuItemNat.setTitle(""); // Hay un caso en el que no renderizamos
    }
}
