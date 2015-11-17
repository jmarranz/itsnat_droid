package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import android.content.Context;

import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;

/**
 * Created by jmarranz on 30/04/14.
 */
public class _DELETE_AttrDescDrawableReflecMethodDimensionIntFloor<TdrawableOrElementDrawable> extends _DELETE_AttrDescDrawableReflecMethodDimensionInt<TdrawableOrElementDrawable>
{
    public _DELETE_AttrDescDrawableReflecMethodDimensionIntFloor(ClassDescDrawable parent, String name, String methodName)
    {
        super(parent,name,methodName);
    }

    public _DELETE_AttrDescDrawableReflecMethodDimensionIntFloor(ClassDescDrawable parent, String name)
    {
        super(parent,name);
    }


    public int getDimensionInt(String attrValue, Context ctx)
    {
        return getDimensionIntFloor(attrValue, ctx);
    }
}
