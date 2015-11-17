package org.itsnat.droid.impl.xmlinflater.shared.attr;

import android.content.Context;
import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.ClassDesc;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescViewReflecFieldSet;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;


/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescReflecFieldSetDimensionInt<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecFieldSet<TclassDesc,TattrTarget,TattrContext>
{
    protected Integer defaultValue;

    public AttrDescReflecFieldSetDimensionInt(TclassDesc parent, String name, String fieldName, Integer defaultValue)
    {
        super(parent,name,fieldName);
        this.defaultValue = defaultValue;
    }

    @Override
    public void setAttribute(TattrTarget target, DOMAttr attr, TattrContext attrCtx)
    {
        int convertedValue = getDimensionInt(attr, attrCtx.getContext());

        setField(target,convertedValue);
    }

    @Override
    public void removeAttribute(TattrTarget target, TattrContext attrCtx)
    {
        if (defaultValue != null)
            setField(target,defaultValue);
    }

    public abstract int getDimensionInt(DOMAttr attr, Context ctx);
}
