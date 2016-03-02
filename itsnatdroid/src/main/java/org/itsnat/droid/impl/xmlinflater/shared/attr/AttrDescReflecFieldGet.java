package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescReflecFieldGet<Treturn,TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDesc<TclassDesc,TattrTarget,TattrContext>
{
    protected FieldContainer<Treturn> field;

    public AttrDescReflecFieldGet(TclassDesc parent, String name, String fieldName)
    {
        super(parent,name);
        this.field = new FieldContainer<Treturn>(parent.getDeclaredClass(),fieldName);
    }

    protected Treturn getField(TattrTarget target)
    {
        return field.get(target);
    }
}
