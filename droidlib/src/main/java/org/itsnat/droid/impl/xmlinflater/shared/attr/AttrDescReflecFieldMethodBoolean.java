package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescReflecFieldMethodBoolean<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecFieldMethod<TclassDesc,TattrTarget,TattrContext>
{
    protected boolean defaultValue;

    public AttrDescReflecFieldMethodBoolean(TclassDesc parent, String name, String fieldName, Class methodClass,String methodName, boolean defaultValue)
    {
        super(parent,name,fieldName,methodClass,methodName,boolean.class);
        this.defaultValue = defaultValue;
    }

    @Override
    public void setAttribute(final TattrTarget target, final DOMAttr attr,final TattrContext attrCtx)
    {
        boolean convertedValue = getBoolean(attr.getValue(),attrCtx.getContext());
        callFieldMethod(target, convertedValue);
    }

    @Override
    public void removeAttribute(TattrTarget target, TattrContext attrCtx)
    {
        callFieldMethod(target, defaultValue);
    }
}
