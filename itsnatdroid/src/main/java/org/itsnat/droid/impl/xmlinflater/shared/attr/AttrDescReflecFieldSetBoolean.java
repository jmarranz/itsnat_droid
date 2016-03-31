package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;


/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescReflecFieldSetBoolean<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
                extends AttrDescReflecFieldSet<Boolean,TclassDesc,TattrTarget,TattrContext>
{
    protected boolean defaultValue;

    public AttrDescReflecFieldSetBoolean(TclassDesc parent, String name, String fieldName, boolean defaultValue)
    {
        super(parent,name,fieldName);
        this.defaultValue = defaultValue;
    }

    @Override
    public void setAttribute(TattrTarget target, DOMAttr attr, TattrContext attrCtx)
    {
        boolean convertedValue = getBoolean(attr.getResourceDesc(), attrCtx.getXMLInflater());

        setField(target,convertedValue);
    }

    @Override
    public void removeAttribute(TattrTarget target, TattrContext attrCtx)
    {
        setField(target,defaultValue);
    }

}
