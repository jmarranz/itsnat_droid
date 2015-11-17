package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;

/**
 * Created by jmarranz on 1/05/14.
 */
public abstract class _DELETED_AttrDescDrawableReflecMethodNameBased<Treturn, TdrawableOrElementDrawable> extends _DELETED_AttrDescDrawableReflecMethod<TdrawableOrElementDrawable>
{
    protected MapSmart<String, Treturn> valueMap;

    public _DELETED_AttrDescDrawableReflecMethodNameBased(ClassDescDrawable parent, String name, String methodName, Class classParam, MapSmart<String, Treturn> valueMap)
    {
        super(parent,name,methodName,classParam);
        this.valueMap = valueMap;
    }

    public _DELETED_AttrDescDrawableReflecMethodNameBased(ClassDescDrawable parent, String name, Class classParam, MapSmart<String, Treturn> valueMap)
    {
        super(parent, name,classParam);
        this.valueMap = valueMap;
    }

    public void setAttribute(TdrawableOrElementDrawable draw, DOMAttr attr, AttrDrawableContext attrCtx)
    {
        Treturn valueRes = parseNameBasedValue(attr.getValue());
        callMethod(draw, valueRes);
    }

    protected abstract Treturn parseNameBasedValue(String value);
}
