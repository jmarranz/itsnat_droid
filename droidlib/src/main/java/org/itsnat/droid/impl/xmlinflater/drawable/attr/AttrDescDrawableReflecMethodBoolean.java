package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawableOrElementDrawableChild;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescDrawableReflecMethodBoolean<TdrawableOrElementDrawable> extends AttrDescDrawableReflecMethod<TdrawableOrElementDrawable>
{
    public AttrDescDrawableReflecMethodBoolean(ClassDescDrawableOrElementDrawableChild parent, String name, String methodName)
    {
        super(parent,name,methodName,getClassParam());
    }

    public AttrDescDrawableReflecMethodBoolean(ClassDescDrawableOrElementDrawableChild parent, String name)
    {
        super(parent,name,getClassParam());
    }

    protected static Class<?> getClassParam()
    {
        return boolean.class;
    }

    @Override
    public void setAttribute(TdrawableOrElementDrawable draw, DOMAttr attr,XMLInflaterDrawable xmlInflaterDrawable,Context ctx)
    {
        boolean convValue = getBoolean(attr.getValue(),ctx);
        callMethod(draw, convValue);
    }
}
