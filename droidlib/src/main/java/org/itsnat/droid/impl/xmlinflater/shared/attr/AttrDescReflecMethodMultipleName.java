package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.ClassDesc;

/**
 * Created by jmarranz on 1/05/14.
 */
public class AttrDescReflecMethodMultipleName<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecMethodNameBased<Integer,TclassDesc,TattrTarget,TattrContext>
{
    public AttrDescReflecMethodMultipleName(TclassDesc parent, String name, String methodName, MapSmart<String, Integer> valueMap, String defaultName)
    {
        super(parent,name,methodName,getClassParam(),valueMap,defaultName);
    }

    public AttrDescReflecMethodMultipleName(TclassDesc parent, String name, MapSmart<String, Integer> valueMap, String defaultName)
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
