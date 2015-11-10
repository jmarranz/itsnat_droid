package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescDrawableReflecMethodDimensionFloatFloor<TdrawableOrElementDrawable> extends AttrDescDrawableReflecMethod<TdrawableOrElementDrawable>
{
    public AttrDescDrawableReflecMethodDimensionFloatFloor(ClassDescDrawable parent, String name, String methodName)
    {
        super(parent,name,methodName,getClassParam());
    }

    public AttrDescDrawableReflecMethodDimensionFloatFloor(ClassDescDrawable parent, String name)
    {
        super(parent,name,getClassParam());
    }

    protected static Class<?> getClassParam()
    {
        return float.class;
    }

    @Override
    public void setAttribute(TdrawableOrElementDrawable draw, DOMAttr attr, AttrDrawableContext attrCtx)
    {
        float convValue = getDimensionFloatFloor(attr.getValue(), attrCtx.getContext());
        callMethod(draw, convValue);
    }

}
