package org.itsnat.droid.impl.xmlinflater.shared.attr;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.PercFloatImpl;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescReflecMethodPercFloat<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecMethod<TclassDesc,TattrTarget,TattrContext>
{
    protected boolean useObject;
    protected PercFloatImpl defaultValue;

    public AttrDescReflecMethodPercFloat(TclassDesc parent, String name, String methodName,boolean useObject, PercFloatImpl defaultValue)
    {
        super(parent,name,methodName,getClassParam(useObject));
        this.useObject = useObject;
        this.defaultValue = defaultValue;
    }

    public AttrDescReflecMethodPercFloat(TclassDesc parent, String name,boolean useObject, PercFloatImpl defaultValue)
    {
        super(parent,name,getClassParam(useObject));
        this.useObject = useObject;
        this.defaultValue = defaultValue;
    }

    protected static Class<?> getClassParam(boolean useObject)
    {
        return useObject ? PercFloatImpl.class : float.class;
    }

    @Override
    public void setAttribute(TattrTarget target, DOMAttr attr, TattrContext attrCtx)
    {
        PercFloatImpl convValue = getPercFloat(attr.getResourceDesc(), attrCtx.getXMLInflaterContext());
        if (useObject)
        {
            callMethod(target, convValue);
        }
        else
        {
            float value = convValue.toFloatBasedOnDataType();
            callMethod(target, value);
        }
    }

    @Override
    public void removeAttribute(TattrTarget target, TattrContext attrCtx)
    {
        if (defaultValue != null)
            callMethod(target, defaultValue);
    }

}
