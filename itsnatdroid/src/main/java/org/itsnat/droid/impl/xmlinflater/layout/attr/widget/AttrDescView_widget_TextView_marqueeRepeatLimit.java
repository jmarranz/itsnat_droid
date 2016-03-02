package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrCompiledResource;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_marqueeRepeatLimit extends AttrDescReflecMethodInt<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_TextView_marqueeRepeatLimit(ClassDescViewBased parent)
    {
        super(parent,"marqueeRepeatLimit",3);
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        if ("marquee_forever".equals(attr.getValue()))
            attr = DOMAttrCompiledResource.createDOMAttrCompiledResource((DOMAttrCompiledResource) attr, "-1");
        super.setAttribute(view, attr, attrCtx);
    }
}
