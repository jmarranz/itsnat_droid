package org.itsnat.droid.impl.xmlinflater.layout.attr;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescViewTextStyle extends AttrDescView
{
    static Map<String,Integer> valueMap = new HashMap<String,Integer>( 3 );
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

    public void removeAttribute(View view, XMLInflaterLayout xmlInflaterLayout, Context ctx)
    {
        setToRemoveAttribute(view, "normal", xmlInflaterLayout, ctx);
    }

    protected abstract void setTextStyle(View view,int style);
}
