package org.itsnat.droid.impl.xmlinflater.layout.attr;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 1/05/14.
 */
public class AttrDescViewReflecMethodSingleName<T> extends AttrDescViewReflecMethodNameBased<T>
{
    public AttrDescViewReflecMethodSingleName(ClassDescViewBased parent, String name, String methodName, Class classParam, MapSmart<String, T> valueMap, String defaultName)
    {
        super(parent,name,methodName,classParam,valueMap,defaultName);
    }

    public AttrDescViewReflecMethodSingleName(ClassDescViewBased parent, String name, Class classParam, MapSmart<String, T> valueMap, String defaultName)
    {
        super(parent, name,classParam,valueMap, defaultName);
    }

    @Override
    protected T parseNameBasedValue(String value)
    {
        return this.<T>parseSingleName(value, valueMap);
    }
}
