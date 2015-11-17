package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 *
 * Created by jmarranz on 30/04/14.
 */
public class _DELETE_AttrDescViewReflecMethodId extends _DELETED_AttrDescViewReflecMethod
{
    protected Integer defaultValue;

    public _DELETE_AttrDescViewReflecMethodId(ClassDescViewBased parent, String name, String methodName, Integer defaultValue)
    {
        super(parent,name,methodName,getClassParam());
        this.defaultValue = defaultValue;
    }

    public _DELETE_AttrDescViewReflecMethodId(ClassDescViewBased parent, String name, Integer defaultValue)
    {
        super(parent,name,getClassParam());
        this.defaultValue = defaultValue;
    }

    protected static Class<?> getClassParam()
    {
        return int.class;
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        int id = getIdentifierAddIfNecessary(attr.getValue(),attrCtx.getContext());

        callMethod(view, id);
    }

    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        if (defaultValue != null)
            callMethod(view, defaultValue);
    }

}
