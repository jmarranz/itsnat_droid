package org.itsnat.droid.impl.xmlinflater.shared.attr;

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
public class AttrDescReflecFieldSetDimensionWithNameIntRound<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecFieldSet<TclassDesc,TattrTarget,TattrContext>
{
    protected String defaultValue;

    public AttrDescReflecFieldSetDimensionWithNameIntRound(TclassDesc parent, String name, String fieldName, String defaultValue)
    {
        super(parent,name,fieldName);
        this.defaultValue = defaultValue;
    }

    @Override
    public void setAttribute(TattrTarget target, DOMAttr attr, TattrContext attrCtx)
    {
        int convertedValue = getDimensionWithNameIntRound(attr.getValue(), attrCtx.getContext());

        setField(target,convertedValue);
    }

    @Override
    public void removeAttribute(TattrTarget target, TattrContext attrCtx)
    {
        if (defaultValue != null)
            setToRemoveAttribute(target, defaultValue,attrCtx);
    }


}
