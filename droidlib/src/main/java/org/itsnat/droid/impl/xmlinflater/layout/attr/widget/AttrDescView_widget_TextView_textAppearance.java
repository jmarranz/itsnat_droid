package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_textAppearance extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_TextView_textAppearance(ClassDescViewBased parent)
    {
        super(parent,"textAppearance");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        int resId = getIdentifier(attr, attrCtx.getXMLInflaterLayout());

        ((TextView)view).setTextAppearance(attrCtx.getContext(),resId);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        // No se que hacer
    }
}
