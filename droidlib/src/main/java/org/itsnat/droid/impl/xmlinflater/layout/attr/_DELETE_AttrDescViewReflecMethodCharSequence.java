package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class _DELETE_AttrDescViewReflecMethodCharSequence extends _DELETED_AttrDescViewReflecMethod
{
    protected String defaultValue;

    public _DELETE_AttrDescViewReflecMethodCharSequence(ClassDescViewBased parent, String name, String methodName, String defaultValue)
    {
        super(parent,name,methodName,getClassParam());
        this.defaultValue = defaultValue;
    }

    public _DELETE_AttrDescViewReflecMethodCharSequence(ClassDescViewBased parent, String name, String defaultValue)
    {
        super(parent,name,getClassParam());
        this.defaultValue = defaultValue;
    }

    protected static Class<?> getClassParam()
    {
        return CharSequence.class;
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        CharSequence convValue = getText(attr.getValue(), attrCtx.getContext());
        callMethod(view, convValue);
    }

    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        if (defaultValue != null)
            callMethod(view,defaultValue);
    }

}
