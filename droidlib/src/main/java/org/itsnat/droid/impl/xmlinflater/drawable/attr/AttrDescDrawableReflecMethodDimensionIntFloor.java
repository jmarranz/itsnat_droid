package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawableOrElementDrawableChild;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescDrawableReflecMethodDimensionIntFloor<TdrawableOrElementDrawable> extends AttrDescDrawableReflecMethod<TdrawableOrElementDrawable>
{
    public AttrDescDrawableReflecMethodDimensionIntFloor(ClassDescDrawableOrElementDrawableChild parent, String name, String methodName)
    {
        super(parent,name,methodName,getClassParam());
    }

    public AttrDescDrawableReflecMethodDimensionIntFloor(ClassDescDrawableOrElementDrawableChild parent, String name)
    {
        super(parent,name,getClassParam());
    }

    protected static Class<?> getClassParam()
    {
        return int.class;
    }

    @Override
    public void setAttribute(TdrawableOrElementDrawable draw, DOMAttr attr,XMLInflaterDrawable xmlInflaterDrawable,Context ctx)
    {
        int convValue = getDimensionIntFloor(attr.getValue(), ctx);
        callMethod(draw, convValue);
    }

}
