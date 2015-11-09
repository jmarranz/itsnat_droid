package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_imeActionLabel extends AttrDescView
{
    public AttrDescView_widget_TextView_imeActionLabel(ClassDescViewBased parent)
    {
        super(parent,"imeActionLabel");
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        String convertedValue = getString(attr.getValue(),attrCtx.getContext());

        TextView textView = (TextView)view;
        textView.setImeActionLabel(convertedValue,textView.getImeActionId());
    }

    public void removeAttribute(View view, XMLInflaterLayout xmlInflaterLayout, Context ctx)
    {
        setToRemoveAttribute(view, "", xmlInflaterLayout, ctx); // No estoy seguro del valor por defecto
    }

}
