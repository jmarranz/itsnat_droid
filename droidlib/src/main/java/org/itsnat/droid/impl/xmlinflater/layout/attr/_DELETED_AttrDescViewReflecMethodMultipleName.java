package org.itsnat.droid.impl.xmlinflater.layout.attr;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 1/05/14.
 */
public class _DELETED_AttrDescViewReflecMethodMultipleName extends _DELETED_AttrDescViewReflecMethodNameBased<Integer>
{
    public _DELETED_AttrDescViewReflecMethodMultipleName(ClassDescViewBased parent, String name, String methodName, MapSmart<String, Integer> valueMap, String defaultName)
    {
        super(parent,name,methodName,getClassParam(),valueMap,defaultName);
    }

    public _DELETED_AttrDescViewReflecMethodMultipleName(ClassDescViewBased parent, String name, MapSmart<String, Integer> valueMap, String defaultName)
    {
        super(parent, name,getClassParam(),valueMap,defaultName);
    }

    protected static Class<?> getClassParam()
    {
        return int.class;
    }

    @Override
    protected Integer parseNameBasedValue(String value)
    {
        return parseMultipleName(value, valueMap);
    }
}
