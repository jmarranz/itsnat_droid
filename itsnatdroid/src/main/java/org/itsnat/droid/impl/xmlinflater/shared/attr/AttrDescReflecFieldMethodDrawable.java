package org.itsnat.droid.impl.xmlinflater.shared.attr;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescReflecFieldMethodDrawable<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecFieldMethod<TclassDesc,TattrTarget,TattrContext>
{
    protected String defaultValue;

    public AttrDescReflecFieldMethodDrawable(TclassDesc parent, String name, String fieldName, Class methodClass,String methodName,String defaultValue)
    {
        super(parent,name,fieldName,methodClass,methodName,Drawable.class);
        this.defaultValue = defaultValue;
    }

    @Override
    public void setAttribute(final TattrTarget target, final DOMAttr attr,final TattrContext attrCtx)
    {
        Drawable convertedValue = getDrawable(attr.getResourceDesc(),attrCtx.getXMLInflaterContext());
        callFieldMethod(target, convertedValue);
    }

    @Override
    public void removeAttribute(TattrTarget target, TattrContext attrCtx)
    {
        if (defaultValue != null) // Para especificar null se ha de usar "@null"
            setToRemoveAttribute(target, defaultValue,attrCtx); // defaultValue puede ser null (ej attr background), también valdría "@null" en el atributo
    }
}
