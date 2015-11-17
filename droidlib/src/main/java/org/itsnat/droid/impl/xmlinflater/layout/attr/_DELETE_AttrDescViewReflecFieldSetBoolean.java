package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;


/**
 * Created by jmarranz on 30/04/14.
 */
public class _DELETE_AttrDescViewReflecFieldSetBoolean extends AttrDescViewReflecFieldSet
{
    protected boolean defaultValue;

    public _DELETE_AttrDescViewReflecFieldSetBoolean(ClassDescViewBased parent, String name, String fieldName, boolean defaultValue)
    {
        super(parent,name,fieldName);
        this.defaultValue = defaultValue;
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        boolean convertedValue = getBoolean(attr.getValue(),attrCtx.getContext());

        setField(view,convertedValue);
    }

    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setField(view,defaultValue);
    }

}
