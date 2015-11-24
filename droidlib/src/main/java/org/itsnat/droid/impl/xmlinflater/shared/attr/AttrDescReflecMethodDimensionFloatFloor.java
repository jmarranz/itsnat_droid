package org.itsnat.droid.impl.xmlinflater.shared.attr;

import android.content.Context;

import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescReflecMethodDimensionFloatFloor<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecMethodDimensionFloatBase<TclassDesc,TattrTarget,TattrContext>
{
    public AttrDescReflecMethodDimensionFloatFloor(TclassDesc parent, String name, String methodName, Float defaultValue)
    {
        super(parent, name, methodName, defaultValue);
    }

    public AttrDescReflecMethodDimensionFloatFloor(TclassDesc parent, String name, Float defaultValue)
    {
        super(parent,name,defaultValue);
    }

    @Override
    public float getDimensionFloatAbstract(String value, Context ctx)
    {
        return getDimensionFloatFloor(value,ctx);
    }
}
