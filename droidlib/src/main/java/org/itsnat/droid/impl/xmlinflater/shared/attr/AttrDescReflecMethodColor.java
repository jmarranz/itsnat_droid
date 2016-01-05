package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

/**
 * Created by jmarranz on 1/05/14.
 */
public class AttrDescReflecMethodColor<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecMethod<TclassDesc,TattrTarget,TattrContext>
{
    protected String defaultValue;

    public AttrDescReflecMethodColor(TclassDesc parent, String name, String methodName, String defaultValue)
    {
        super(parent,name,methodName,getClassParam());
        this.defaultValue = defaultValue;
    }

    public AttrDescReflecMethodColor(TclassDesc parent, String name, String defaultValue)
    {
        super(parent, name,getClassParam());
        this.defaultValue = defaultValue;
    }

    public AttrDescReflecMethodColor(TclassDesc parent, String name, String methodName, int defaultValue)
    {
        super(parent,name,methodName,getClassParam());
        this.defaultValue = XMLInflateRegistry.toStringColorTransparent(defaultValue);
    }

    public AttrDescReflecMethodColor(TclassDesc parent, String name, int defaultValue)
    {
        super(parent, name,getClassParam());
        this.defaultValue = XMLInflateRegistry.toStringColorTransparent(defaultValue);
    }


    protected static Class<?> getClassParam()
    {
        return int.class;
    }

    @Override
    public void setAttribute(TattrTarget target, DOMAttr attr, TattrContext attrCtx)
    {
        int convValue = getColor(attr, attrCtx.getContext(),attrCtx.getXMLInflater());
        callMethod(target, convValue);
    }

    @Override
    public void removeAttribute(TattrTarget target, TattrContext attrCtx)
    {
        if (defaultValue != null)
            callMethod(target, defaultValue);
    }
}
