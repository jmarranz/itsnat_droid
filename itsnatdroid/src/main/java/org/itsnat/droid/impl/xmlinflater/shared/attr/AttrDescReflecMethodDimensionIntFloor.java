package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

/**
 * Aunque la entrada de datos sea una dimensión float con sufijo y todo, el método que define el valor sólo admite un entero
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescReflecMethodDimensionIntFloor<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecMethodDimensionIntBase<TclassDesc,TattrTarget,TattrContext>
{
    public AttrDescReflecMethodDimensionIntFloor(TclassDesc parent, String name, String methodName, Float defaultValue)
    {
        super(parent,name,methodName,defaultValue);
    }

    public AttrDescReflecMethodDimensionIntFloor(TclassDesc parent, String name, Float defaultValue)
    {
        super(parent,name,defaultValue);
    }

    @Override
    public int getDimensionIntAbstract(DOMAttr attr, XMLInflaterContext xmlInflaterContext)
    {
        return getDimensionIntFloor(attr.getResourceDesc(), xmlInflaterContext);
    }

}
