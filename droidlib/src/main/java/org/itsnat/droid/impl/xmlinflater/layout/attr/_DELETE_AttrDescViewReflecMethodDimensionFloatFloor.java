package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class _DELETE_AttrDescViewReflecMethodDimensionFloatFloor extends _DELETED_AttrDescViewReflecMethod
{
    protected Float defaultValue;

    public _DELETE_AttrDescViewReflecMethodDimensionFloatFloor(ClassDescViewBased parent, String name, String methodName, Float defaultValue)
    {
        super(parent,name,methodName,getClassParam());
        this.defaultValue = defaultValue;
    }

    public _DELETE_AttrDescViewReflecMethodDimensionFloatFloor(ClassDescViewBased parent, String name, Float defaultValue)
    {
        super(parent,name,getClassParam());
        this.defaultValue = defaultValue;
    }

    protected static Class<?> getClassParam()
    {
        return float.class;
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        float convValue = getDimensionFloatFloor(attr.getValue(),attrCtx.getContext());
        callMethod(view, convValue);
    }

    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        // En el caso de defaultValue nulo es que no sabemos qué poner, es el caso por ejemplo de poner a cero el tamaño texto, no tiene sentido, se tendría que extraer el tamaño por defecto del Theme actual, un follón y total será muy raro
        if (defaultValue != null)
            callMethod(view, defaultValue);
    }

}
