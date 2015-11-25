package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescReflecFieldSetInt<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecFieldSet<Integer,TclassDesc,TattrTarget,TattrContext>
{
    protected int defaultValue;

    public AttrDescReflecFieldSetInt(TclassDesc parent, String name, String fieldName, int defaultValue)
    {
        super(parent,name,fieldName);
        this.defaultValue = defaultValue;
    }

    @Override
    public void setAttribute(TattrTarget target, DOMAttr attr, TattrContext attrCtx)
    {
        int convertedValue = getInteger(attr.getValue(),attrCtx.getContext());

        setField(target,convertedValue);
    }

    @Override
    public void removeAttribute(TattrTarget target, TattrContext attrCtx)
    {
        setField(target,defaultValue);
    }

}
