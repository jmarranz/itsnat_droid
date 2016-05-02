package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetBoolean;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_SlidingDrawer_orientation extends AttrDescReflecFieldSetBoolean<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_SlidingDrawer_orientation(ClassDescViewBased parent)
    {
        super(parent,"orientation","mVertical",true);
    }

    @Override
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

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setAttributeToRemove(view, "vertical",attrCtx);
    }


}
