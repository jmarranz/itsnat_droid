package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Aunque la entrada de datos sea una dimensión float con sufijo y todo, el método que define el valor sólo admite un entero
 * Created by jmarranz on 30/04/14.
 */
public class _DELETE_AttrDescViewReflecMethodDimensionIntRound extends _DELETE_AttrDescViewReflecMethodDimensionInt
{
    public _DELETE_AttrDescViewReflecMethodDimensionIntRound(ClassDescViewBased parent, String name, String methodName, Float defaultValue)
    {
        super(parent,name,methodName,defaultValue);
    }

    public _DELETE_AttrDescViewReflecMethodDimensionIntRound(ClassDescViewBased parent, String name, Float defaultValue)
    {
        super(parent,name,defaultValue);
    }

    @Override
    public int getDimensionInt(DOMAttr attr, Context ctx)
    {
        return getDimensionIntRound(attr.getValue(), ctx);
    }
}
