package org.itsnat.droid.impl.xmlinflater.shared.attr;

import android.view.animation.Interpolator;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.AttrContext;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDesc;

/**
 *
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescReflecMethodInterpolator<TclassDesc extends ClassDesc,TattrTarget,TattrContext extends AttrContext>
        extends AttrDescReflecMethod<TclassDesc,TattrTarget,TattrContext>
{
    protected String defaultValue;

    public AttrDescReflecMethodInterpolator(TclassDesc parent, String name, String methodName, String defaultValue)
    {
        super(parent,name,methodName,getClassParam());
        this.defaultValue = defaultValue;
    }

    public AttrDescReflecMethodInterpolator(TclassDesc parent, String name, String defaultValue)
    {
        super(parent,name,getClassParam());
        this.defaultValue = defaultValue;
    }

    protected static Class<?> getClassParam()
    {
        return Interpolator.class;
    }

    @Override
    public void setAttribute(TattrTarget target,DOMAttr attr, TattrContext attrCtx)
    {
        Interpolator interpolator = getInterpolator(attr.getResourceDesc(), attrCtx.getXMLInflaterContext());

        callMethod(target, interpolator);
    }

    @Override
    public void removeAttribute(TattrTarget target, TattrContext attrCtx)
    {
        if (defaultValue != null)
            setAttributeToRemove(target, defaultValue, attrCtx);
    }

}
