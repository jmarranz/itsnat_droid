package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.ClassDesc;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescReflecFieldSet<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDesc<TclassDesc,TattrTarget,TattrContext>
{
    protected FieldContainer field;

    @SuppressWarnings("unchecked")
    public AttrDescReflecFieldSet(TclassDesc parent, String name, String fieldName)
    {
        super(parent,name);
        this.field = new FieldContainer(parent.getDeclaredClass(),fieldName);
    }

    @SuppressWarnings("unchecked")
    protected void setField(TattrTarget target,Object convertedValue)
    {
        field.set(target, convertedValue);
    }
}
