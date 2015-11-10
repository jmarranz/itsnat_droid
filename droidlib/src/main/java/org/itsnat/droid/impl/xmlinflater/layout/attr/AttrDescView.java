package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrLocalResource;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflater.attr.AttrDesc;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescView extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext> // TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext
{
    private static Class class_R_styleable;

    public AttrDescView(ClassDescViewBased parent, String name)
    {
        super(parent,name);
    }

    protected static Class getClass_R_styleable()
    {
        if (class_R_styleable == null)
            class_R_styleable = MiscUtil.resolveClass("com.android.internal.R$styleable");
        return class_R_styleable;
    }


    protected void setToRemoveAttribute(View view, String value, AttrLayoutContext attrCtx)
    {
        // Este método es llamado desde removeAttribute, cuyo valor será o @null o un recurso de Android, no esperamos
        // nada dinámico (Remote o Asset), por eso hacemos cast sin complejos a DOMAttrLocalResource
        DOMAttrLocalResource attr = (DOMAttrLocalResource) DOMAttr.create(InflatedXML.XMLNS_ANDROID, getName(), value);

        setAttribute(view, attr,attrCtx);
    }

    public abstract void setAttribute(View view,DOMAttr attr,AttrLayoutContext attrCtx);

    public abstract void removeAttribute(View view, AttrLayoutContext attrCtx);
}


