package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

/**
 * Created by jmarranz on 1/05/14.
 */
public class AttrDescReflecFieldSetColor<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecFieldSet<Integer,TclassDesc,TattrTarget,TattrContext>
{
    protected String defaultValue;

    public AttrDescReflecFieldSetColor(TclassDesc parent, String name, String fieldName, String defaultValue)
    {
        super(parent,name,fieldName);
        this.defaultValue = defaultValue;
    }

    public AttrDescReflecFieldSetColor(TclassDesc parent, String name, String fieldName, int defaultValue)
    {
        super(parent,name,fieldName);
        this.defaultValue = XMLInflateRegistry.toStringColorTransparent(defaultValue);
    }

    @Override
    public void setAttribute(TattrTarget target, DOMAttr attr, TattrContext attrCtx)
    {
        int convertedValue = getColor(attr,attrCtx.getXMLInflater());

        setField(target,convertedValue);
    }

    @Override
    public void removeAttribute(TattrTarget target, TattrContext attrCtx)
    {
        if (defaultValue != null)
            setToRemoveAttribute(target, defaultValue,attrCtx);
    }
}
