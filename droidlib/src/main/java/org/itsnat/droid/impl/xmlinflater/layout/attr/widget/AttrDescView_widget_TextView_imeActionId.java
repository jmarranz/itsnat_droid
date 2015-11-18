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
public class AttrDescView_widget_TextView_imeActionId extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_TextView_imeActionId(ClassDescViewBased parent)
    {
        super(parent,"imeActionId");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        int convertedValue = getInteger(attr.getValue(),attrCtx.getContext());

        TextView textView = (TextView)view;
        textView.setImeActionLabel(textView.getImeActionLabel(),convertedValue);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "0",attrCtx);
    }

}
