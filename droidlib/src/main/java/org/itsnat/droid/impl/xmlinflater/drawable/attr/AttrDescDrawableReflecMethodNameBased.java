package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawableOrElementDrawableChild;

import java.util.Map;

/**
 * Created by jmarranz on 1/05/14.
 */
public abstract class AttrDescDrawableReflecMethodNameBased<Treturn, TdrawableOrElementDrawable> extends AttrDescDrawableReflecMethod<TdrawableOrElementDrawable>
{
    protected Map<String, Treturn> valueMap;

    public AttrDescDrawableReflecMethodNameBased(ClassDescDrawableOrElementDrawableChild parent, String name, String methodName, Class classParam, Map<String, Treturn> valueMap)
    {
        super(parent,name,methodName,classParam);
        this.valueMap = valueMap;
    }

    public AttrDescDrawableReflecMethodNameBased(ClassDescDrawableOrElementDrawableChild parent, String name, Class classParam, Map<String, Treturn> valueMap)
    {
        super(parent, name,classParam);
        this.valueMap = valueMap;
    }

    public void setAttribute(TdrawableOrElementDrawable draw, DOMAttr attr, XMLInflaterDrawable xmlInflater, Context ctx)
    {
        Treturn valueRes = parseNameBasedValue(attr.getValue());
        callMethod(draw, valueRes);
    }

    protected abstract Treturn parseNameBasedValue(String value);
}
