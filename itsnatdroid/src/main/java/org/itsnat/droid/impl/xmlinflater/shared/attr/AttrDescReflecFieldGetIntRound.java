package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescReflecFieldGetIntRound<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecFieldGet<Integer,TclassDesc,TattrTarget,TattrContext>
{
    protected Integer defaultValue;

    public AttrDescReflecFieldGetIntRound(TclassDesc parent, String name, String fieldName, Integer defaultValue)
    {
        super(parent,name,fieldName);
    }


    @Override
    public void setAttribute(TattrTarget target, DOMAttr attr, TattrContext attrCtx)
    {
        int convValue = getDimensionIntRound(attr.getResourceDesc(), attrCtx.getXMLInflaterContext());

        callMethod(target, convValue);
    }

    @Override
    public void removeAttribute(TattrTarget target, TattrContext attrCtx)
    {
        if (defaultValue != null)
            callMethod(target,defaultValue);
    }

    protected abstract void callMethod(TattrTarget target,int value);

}
