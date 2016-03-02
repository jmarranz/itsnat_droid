package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescReflecFieldSet<TfieldClass,TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDesc<TclassDesc,TattrTarget,TattrContext>
{
    protected FieldContainer<TfieldClass> field;

    public AttrDescReflecFieldSet(TclassDesc parent, String name, String fieldName)
    {
        super(parent,name);
        this.field = new FieldContainer<TfieldClass>(parent.getDeclaredClass(),fieldName);
    }

    protected void setField(TattrTarget target,TfieldClass convertedValue)
    {
        field.set(target, convertedValue);
    }
}
