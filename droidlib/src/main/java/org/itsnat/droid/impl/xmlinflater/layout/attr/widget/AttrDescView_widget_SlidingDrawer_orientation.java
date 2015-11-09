package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.content.Context;
import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescViewReflecFieldSetBoolean;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_SlidingDrawer_orientation extends AttrDescViewReflecFieldSetBoolean
{
    public AttrDescView_widget_SlidingDrawer_orientation(ClassDescViewBased parent)
    {
        super(parent,"orientation","mVertical",true);
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        String value = attr.getValue();
        boolean vertical = true;
        if (value.equals("horizontal"))
            vertical = false;
        else if (value.equals("vertical"))
            vertical = true;

        setField(view,vertical);
    }

    public void removeAttribute(View view, XMLInflaterLayout xmlInflaterLayout, Context ctx)
    {
        setToRemoveAttribute(view, "vertical", xmlInflaterLayout, ctx);
    }


}
