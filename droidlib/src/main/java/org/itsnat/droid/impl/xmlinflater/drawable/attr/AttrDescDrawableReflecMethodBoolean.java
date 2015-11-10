package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescDrawableReflecMethodBoolean<TdrawableOrElementDrawable> extends AttrDescDrawableReflecMethod<TdrawableOrElementDrawable>
{
    public AttrDescDrawableReflecMethodBoolean(ClassDescDrawable parent, String name, String methodName)
    {
        super(parent,name,methodName,getClassParam());
    }

    public AttrDescDrawableReflecMethodBoolean(ClassDescDrawable parent, String name)
    {
        super(parent,name,getClassParam());
    }

    protected static Class<?> getClassParam()
    {
        return boolean.class;
    }

    @Override
    public void setAttribute(TdrawableOrElementDrawable draw, DOMAttr attr, AttrDrawableContext attrCtx)
    {
        boolean convValue = getBoolean(attr.getValue(),attrCtx.getContext());
        callMethod(draw, convValue);
    }
}
