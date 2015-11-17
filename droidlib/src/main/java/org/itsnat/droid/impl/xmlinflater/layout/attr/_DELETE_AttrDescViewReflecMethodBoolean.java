package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class _DELETE_AttrDescViewReflecMethodBoolean extends _DELETED_AttrDescViewReflecMethod
{
    protected boolean defaultValue;

    public _DELETE_AttrDescViewReflecMethodBoolean(ClassDescViewBased parent, String name, String methodName, boolean defaultValue)
    {
        super(parent,name,methodName,getClassParam());
        this.defaultValue = defaultValue;
    }

    public _DELETE_AttrDescViewReflecMethodBoolean(ClassDescViewBased parent, String name, boolean defaultValue)
    {
        super(parent,name,getClassParam());
        this.defaultValue = defaultValue;
    }

    protected static Class<?> getClassParam()
    {
        return boolean.class;
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        boolean convValue = getBoolean(attr.getValue(),attrCtx.getContext());
        callMethod(view, convValue);
    }

    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        callMethod(view, defaultValue);
    }
}
