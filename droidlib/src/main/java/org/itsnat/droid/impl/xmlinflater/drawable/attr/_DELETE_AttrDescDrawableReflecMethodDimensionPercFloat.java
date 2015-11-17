package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.PercFloat;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;

/**
 * Created by jmarranz on 30/04/14.
 */
public class _DELETE_AttrDescDrawableReflecMethodDimensionPercFloat<TdrawableOrElementDrawable> extends _DELETED_AttrDescDrawableReflecMethod<TdrawableOrElementDrawable>
{
    public _DELETE_AttrDescDrawableReflecMethodDimensionPercFloat(ClassDescDrawable parent, String name, String methodName)
    {
        super(parent,name,methodName,getClassParam());
    }

    public _DELETE_AttrDescDrawableReflecMethodDimensionPercFloat(ClassDescDrawable parent, String name)
    {
        super(parent,name,getClassParam());
    }

    protected static Class<?> getClassParam()
    {
        return PercFloat.class;
    }

    @Override
    public void setAttribute(TdrawableOrElementDrawable draw, DOMAttr attr, AttrDrawableContext attrCtx)
    {
        PercFloat convValue = getDimensionPercFloat(attr.getValue(), attrCtx.getContext());
        callMethod(draw, convValue);
    }

}
