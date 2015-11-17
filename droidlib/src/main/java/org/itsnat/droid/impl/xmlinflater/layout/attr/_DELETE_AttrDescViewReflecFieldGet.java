package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.view.View;

import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class _DELETE_AttrDescViewReflecFieldGet extends AttrDescView
{
    protected FieldContainer field;

    @SuppressWarnings("unchecked")
    public _DELETE_AttrDescViewReflecFieldGet(ClassDescViewBased parent, String name, String fieldName)
    {
        super(parent,name);
        this.field = new FieldContainer(parent.getDeclaredClass(),fieldName);
    }

    protected Object getField(View view)
    {
        return field.get(view);
    }
}
