package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.content.Context;
import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.OneTimeAttrProcess;
import org.itsnat.droid.impl.xmlinflater.layout.PendingPostInsertChildrenTasks;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Aunque la entrada de datos sea una dimensión float con sufijo y todo, el método que define el valor sólo admite un entero
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescViewReflecMethodDimensionIntFloor extends AttrDescViewReflecMethodDimensionInt
{
    public AttrDescViewReflecMethodDimensionIntFloor(ClassDescViewBased parent, String name, String methodName, Float defaultValue)
    {
        super(parent,name,methodName,defaultValue);
    }

    public AttrDescViewReflecMethodDimensionIntFloor(ClassDescViewBased parent, String name, Float defaultValue)
    {
        super(parent,name,defaultValue);
    }

    @Override
    public int getDimensionInt(DOMAttr attr, Context ctx)
    {
        return getDimensionIntFloor(attr.getValue(), ctx);
    }

}
