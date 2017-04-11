package org.itsnat.droid.impl.xmlinflater.menu.attr;

import android.view.MenuItem;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflated.menu.ElementMenuChildMenuItem;
import org.itsnat.droid.impl.xmlinflater.menu.AttrMenuContext;
import org.itsnat.droid.impl.xmlinflater.menu.classtree.ClassDescElementMenuChildBased;
import org.itsnat.droid.impl.xmlinflater.menu.classtree.ClassDescElementMenuChildMenuItem;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameMultiple;

public class AttrDescMenu_MenuItem_showAsAction extends AttrDescReflecMethodNameMultiple<ClassDescElementMenuChildBased,ElementMenuChildMenuItem,AttrMenuContext>
{
    public static final MapSmart<String,Integer> showAsActionMap = MapSmart.<String,Integer>create(4);
    static
    {
        showAsActionMap.put("ifRoom", MenuItem.SHOW_AS_ACTION_IF_ROOM);
        showAsActionMap.put("withText", MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        showAsActionMap.put("never", MenuItem.SHOW_AS_ACTION_NEVER);
        showAsActionMap.put("always", MenuItem.SHOW_AS_ACTION_ALWAYS);
        showAsActionMap.put("collapseActionView", MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
    }

    public AttrDescMenu_MenuItem_showAsAction(ClassDescElementMenuChildMenuItem parent)
    {
        super(parent, "showAsAction", showAsActionMap, "");
    }

    @Override
    public void setAttribute(ElementMenuChildMenuItem target, DOMAttr attr, AttrMenuContext attrCtx)
    {
        Integer valueRes = parseNameBasedValue(attr.getValue());
        target.getMenuItem().setShowAsAction(valueRes);
    }

    @Override
    public void removeAttribute(ElementMenuChildMenuItem target, AttrMenuContext attrCtx)
    {
        if (defaultName != null)
        {
            //if (defaultName.equals("")) callMethod(target, -1); // Android utiliza el -1 de vez en cuando como valor por defecto
            //else setAttributeToRemove(target, defaultName,attrCtx);
        }
    }

}