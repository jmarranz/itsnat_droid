package org.itsnat.droid.impl.xmlinflater.shared.attr;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.ClassDesc;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescReflecFieldSetDimensionIntFloor<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
            extends AttrDescReflecFieldSetDimensionIntBase<TclassDesc,TattrTarget,TattrContext>
{
    public AttrDescReflecFieldSetDimensionIntFloor(TclassDesc parent, String name, String fieldName, Integer defaultValue)
    {
        super(parent,name,fieldName,defaultValue);
    }

    @Override
    public int getDimensionInt(DOMAttr attr, Context ctx)
    {
        return getDimensionIntFloor(attr.getValue(), ctx);
    }
}
