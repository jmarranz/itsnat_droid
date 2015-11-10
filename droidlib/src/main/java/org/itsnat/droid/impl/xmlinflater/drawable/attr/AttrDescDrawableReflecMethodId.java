package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;

/**
 *
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescDrawableReflecMethodId<TdrawableOrElementDrawable> extends AttrDescDrawableReflecMethod<TdrawableOrElementDrawable>
{
    public AttrDescDrawableReflecMethodId(ClassDescDrawable parent, String name, String methodName)
    {
        super(parent,name,methodName,getClassParam());
    }

    public AttrDescDrawableReflecMethodId(ClassDescDrawable parent, String name)
    {
        super(parent,name,getClassParam());
    }

    protected static Class<?> getClassParam()
    {
        return int.class;
    }

    @Override
    public void setAttribute(final TdrawableOrElementDrawable draw, final DOMAttr attr, AttrDrawableContext attrCtx)
    {
        int id = getIdentifierAddIfNecessary(attr.getValue(),attrCtx.getContext());

        callMethod(draw, id);
    }

}
