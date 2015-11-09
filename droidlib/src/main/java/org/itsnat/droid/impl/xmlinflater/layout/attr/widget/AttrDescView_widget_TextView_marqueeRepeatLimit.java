package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrLocalResource;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescViewReflecMethodInt;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_marqueeRepeatLimit extends AttrDescViewReflecMethodInt
{
    public AttrDescView_widget_TextView_marqueeRepeatLimit(ClassDescViewBased parent)
    {
        super(parent,"marqueeRepeatLimit",3);
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        if ("marquee_forever".equals(attr.getValue()))
            attr = DOMAttrLocalResource.createDOMAttrLocalResource((DOMAttrLocalResource)attr, "-1");
        super.setAttribute(view, attr, attrCtx);
    }
}
