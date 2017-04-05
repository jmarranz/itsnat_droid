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
        menuItem.getMenuItem().setTitle(title);
    }

    @Override
    public void removeAttribute(ElementMenuChildMenuItem menuItem, AttrMenuContext attrCtx)
    {
        menuItem.getMenuItem().setTitle("");
    }
}
