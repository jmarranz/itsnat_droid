package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.graphics.Typeface;
import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescViewTextStyle extends AttrDescView
{
    @SuppressWarnings("unchecked")
    public static final MapSmart<String,Integer> valueMap = MapSmart.<String,Integer>create( 3 );
    static
    {
        valueMap.put("normal", Typeface.NORMAL); // 0
        valueMap.put("bold",   Typeface.BOLD);   // 1
        valueMap.put("italic", Typeface.ITALIC); // 2
    }

    public AttrDescViewTextStyle(ClassDescViewBased parent, String name)
    {
        super(parent,name);
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        int style = parseMultipleName(attr.getValue(), valueMap);
        setTextStyle(view,style);
    }

    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "normal",attrCtx);
    }

    protected abstract void setTextStyle(View view,int style);
}
