package org.itsnat.droid.impl.xmlinflater.shared.attr;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

/**
 * Created by jmarranz on 1/05/14.
 */
public class AttrDescReflecMethodDrawable<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecMethod<TclassDesc,TattrTarget,TattrContext>
{
    protected String defaultValue;

    public AttrDescReflecMethodDrawable(TclassDesc parent, String name, String methodName, String defaultValue)
    {
        super(parent,name,methodName,getClassParam());
        this.defaultValue = defaultValue;
    }

    public AttrDescReflecMethodDrawable(TclassDesc parent, String name, String defaultValue)
    {
        super(parent, name,getClassParam());
        this.defaultValue = defaultValue;
    }

    protected static Class<?> getClassParam()
    {
        return Drawable.class;
    }

    @Override
    public void setAttribute(final TattrTarget target,final DOMAttr attr, final TattrContext attrCtx)
    {
        Drawable convValue = getDrawable(attr,attrCtx.getContext(),attrCtx.getXMLInflater());
        callMethod(target, convValue);
    }

    @Override
    public void removeAttribute(TattrTarget target, TattrContext attrCtx)
    {
        if (defaultValue != null) // Para especificar null se ha de usar "@null"
            setToRemoveAttribute(target, defaultValue,attrCtx); // defaultValue puede ser null (ej attr background), también valdría "@null" en el atributo
    }
}
