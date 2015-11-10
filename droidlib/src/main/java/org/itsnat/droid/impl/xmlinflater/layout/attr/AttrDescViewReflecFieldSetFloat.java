package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescViewReflecFieldSetFloat extends AttrDescViewReflecFieldSet
{
    protected Float defaultValue;

    public AttrDescViewReflecFieldSetFloat(ClassDescViewBased parent, String name, String fieldName, Float defaultValue)
    {
        super(parent,name,fieldName);
        this.defaultValue = defaultValue;
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        float convertedValue = getFloat(attr.getValue(),attrCtx.getContext());

        setField(view,convertedValue);
    }

    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setField(view,defaultValue);
    }

}
