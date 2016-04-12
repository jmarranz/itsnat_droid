package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

/**
 *
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescReflecMethodId<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecMethod<TclassDesc,TattrTarget,TattrContext>
{
    protected Integer defaultValue;

    public AttrDescReflecMethodId(TclassDesc parent, String name, String methodName, Integer defaultValue)
    {
        super(parent,name,methodName,getClassParam());
        this.defaultValue = defaultValue;
    }

    public AttrDescReflecMethodId(TclassDesc parent, String name, Integer defaultValue)
    {
        super(parent,name,getClassParam());
        this.defaultValue = defaultValue;
    }

    protected static Class<?> getClassParam()
    {
        return int.class;
    }

    @Override
    public void setAttribute(TattrTarget target,DOMAttr attr, TattrContext attrCtx)
    {
        int id = getIdentifier(attr.getResourceDesc(), attrCtx.getXMLInflaterContext());

        callMethod(target, id);
    }

    @Override
    public void removeAttribute(TattrTarget target, TattrContext attrCtx)
    {
        if (defaultValue != null)
            callMethod(target, defaultValue);
    }

}
