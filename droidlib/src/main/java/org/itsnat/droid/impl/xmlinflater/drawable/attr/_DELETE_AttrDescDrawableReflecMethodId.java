package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;

/**
 *
 * Created by jmarranz on 30/04/14.
 */
public class _DELETE_AttrDescDrawableReflecMethodId<TdrawableOrElementDrawable> extends _DELETED_AttrDescDrawableReflecMethod<TdrawableOrElementDrawable>
{
    public _DELETE_AttrDescDrawableReflecMethodId(ClassDescDrawable parent, String name, String methodName)
    {
        super(parent,name,methodName,getClassParam());
    }

    public _DELETE_AttrDescDrawableReflecMethodId(ClassDescDrawable parent, String name)
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
