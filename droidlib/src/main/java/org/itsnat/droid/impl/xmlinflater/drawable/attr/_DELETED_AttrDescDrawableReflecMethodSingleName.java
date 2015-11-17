package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;

/**
 * Created by jmarranz on 1/05/14.
 */
public class _DELETED_AttrDescDrawableReflecMethodSingleName<Treturn, TdrawableOrElementDrawable> extends _DELETED_AttrDescDrawableReflecMethodNameBased<Treturn, TdrawableOrElementDrawable>
{
    public _DELETED_AttrDescDrawableReflecMethodSingleName(ClassDescDrawable parent, String name, String methodName, Class classParam, MapSmart<String, Treturn> valueMap)
    {
        super(parent,name,methodName,classParam,valueMap);
    }

    public _DELETED_AttrDescDrawableReflecMethodSingleName(ClassDescDrawable parent, String name, Class classParam, MapSmart<String, Treturn> valueMap)
    {
        super(parent, name,classParam,valueMap);
    }

    @Override
    protected Treturn parseNameBasedValue(String value)
    {
        return this.<Treturn>parseSingleName(value, valueMap);
    }
}
