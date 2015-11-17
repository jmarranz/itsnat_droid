package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.ClassDesc;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescReflecFieldGet<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDesc<TclassDesc,TattrTarget,TattrContext>
{
    protected FieldContainer field;

    @SuppressWarnings("unchecked")
    public AttrDescReflecFieldGet(TclassDesc parent, String name, String fieldName)
    {
        super(parent,name);
        this.field = new FieldContainer(parent.getDeclaredClass(),fieldName);
    }

    protected Object getField(TattrTarget target)
    {
        return field.get(target);
    }
}
