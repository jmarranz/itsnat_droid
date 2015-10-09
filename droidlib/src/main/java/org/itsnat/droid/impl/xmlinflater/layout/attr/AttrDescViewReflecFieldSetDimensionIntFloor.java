package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.content.Context;
import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.OneTimeAttrProcess;
import org.itsnat.droid.impl.xmlinflater.layout.PendingPostInsertChildrenTasks;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescViewReflecFieldSetDimensionIntFloor extends AttrDescViewReflecFieldSetDimensionInt
{
    public AttrDescViewReflecFieldSetDimensionIntFloor(ClassDescViewBased parent, String name, String fieldName, Integer defaultValue)
    {
        super(parent,name,fieldName,defaultValue);
    }

    @Override
    public int getDimensionInt(DOMAttr attr, Context ctx)
    {
        return getDimensionIntFloor(attr.getValue(), ctx);
    }
}
