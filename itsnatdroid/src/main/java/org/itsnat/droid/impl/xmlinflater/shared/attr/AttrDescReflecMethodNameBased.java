package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

/**
 * Created by jmarranz on 1/05/14.
 */
public abstract class AttrDescReflecMethodNameBased<Treturn,TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecMethod<TclassDesc,TattrTarget,TattrContext>
{
    protected MapSmart<String, Treturn> valueMap;
    protected String defaultName;

    public AttrDescReflecMethodNameBased(TclassDesc parent, String name, String methodName, Class classParam, MapSmart<String, Treturn> valueMap, String defaultName)
    {
        super(parent,name,methodName,classParam);
        this.valueMap = valueMap;
        this.defaultName = defaultName;
    }

    public AttrDescReflecMethodNameBased(TclassDesc parent, String name, Class classParam, MapSmart<String, Treturn> valueMap, String defaultName)
    {
        super(parent, name,classParam);
        this.valueMap = valueMap;
        this.defaultName = defaultName;
    }

    @Override
    public void setAttribute(TattrTarget target,DOMAttr attr, TattrContext attrCtx)
    {
        Treturn valueRes = parseNameBasedValue(attr.getValue());
        callMethod(target, valueRes);
    }

    @Override
    public void removeAttribute(TattrTarget target, TattrContext attrCtx)
    {
        if (defaultName != null)
        {
            if (defaultName.equals("")) callMethod(target, -1); // Android utiliza el -1 de vez en cuando como valor por defecto
            else setToRemoveAttribute(target, defaultName,attrCtx);
        }
    }

    protected abstract Treturn parseNameBasedValue(String value);
}
