package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.content.Context;
import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;


/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class _DELETE_AttrDescViewReflecFieldSetDimensionInt extends AttrDescViewReflecFieldSet
{
    protected Integer defaultValue;

    public _DELETE_AttrDescViewReflecFieldSetDimensionInt(ClassDescViewBased parent, String name, String fieldName, Integer defaultValue)
    {
        super(parent,name,fieldName);
        this.defaultValue = defaultValue;
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        int convertedValue = getDimensionInt(attr, attrCtx.getContext());

        setField(view,convertedValue);
    }

    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        if (defaultValue != null)
            setField(view,defaultValue);
    }

    public abstract int getDimensionInt(DOMAttr attr, Context ctx);
}
