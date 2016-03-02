package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

/**
 * Created by jmarranz on 1/05/14.
 */
public class AttrDescReflecMethodNameSingle<Treturn,TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
                extends AttrDescReflecMethodNameBased<Treturn,TclassDesc,TattrTarget,TattrContext>
{
    public AttrDescReflecMethodNameSingle(TclassDesc parent, String name, String methodName, Class classParam, MapSmart<String, Treturn> valueMap, String defaultName)
    {
        super(parent,name,methodName,classParam,valueMap,defaultName);
    }

    public AttrDescReflecMethodNameSingle(TclassDesc parent, String name, Class classParam, MapSmart<String, Treturn> valueMap, String defaultName)
    {
        super(parent, name,classParam,valueMap, defaultName);
    }

    @Override
    protected Treturn parseNameBasedValue(String value)
    {
        return AttrDesc.<Treturn>parseSingleName(value, valueMap);
    }
}
