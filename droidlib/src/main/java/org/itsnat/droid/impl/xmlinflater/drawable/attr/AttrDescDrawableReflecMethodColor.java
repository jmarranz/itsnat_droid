package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawableOrElementDrawableChild;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescDrawableReflecMethodColor<TdrawableOrElementDrawable> extends AttrDescDrawableReflecMethod<TdrawableOrElementDrawable>
{
    public AttrDescDrawableReflecMethodColor(ClassDescDrawableOrElementDrawableChild parent, String name, String methodName)
    {
        super(parent,name,methodName,getClassParam());
    }

    public AttrDescDrawableReflecMethodColor(ClassDescDrawableOrElementDrawableChild parent, String name)
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
        int convValue = getColor(attr.getValue(),ctx);
        callMethod(draw, convValue);
    }
}
