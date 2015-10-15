package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawableOrElementDrawableChild;

import java.util.Map;

/**
 * Created by jmarranz on 1/05/14.
 */
public class AttrDescDrawableReflecMethodSingleName<Treturn, TdrawableOrElementDrawable> extends AttrDescDrawableReflecMethodNameBased<Treturn, TdrawableOrElementDrawable>
{
    public AttrDescDrawableReflecMethodSingleName(ClassDescDrawableOrElementDrawableChild parent, String name, String methodName, Class classParam, Map<String,Treturn> valueMap)
    {
        super(parent,name,methodName,classParam,valueMap);
    }

    public AttrDescDrawableReflecMethodSingleName(ClassDescDrawableOrElementDrawableChild parent, String name, Class classParam, Map<String,Treturn> valueMap)
    {
        super(parent, name,classParam,valueMap);
    }

    @Override
    protected Treturn parseNameBasedValue(String value)
    {
        return this.<Treturn>parseSingleName(value, valueMap);
    }
}
