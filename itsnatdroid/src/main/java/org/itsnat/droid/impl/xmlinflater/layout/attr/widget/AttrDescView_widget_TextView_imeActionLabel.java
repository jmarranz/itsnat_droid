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
public class AttrDescView_widget_TextView_imeActionLabel extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_TextView_imeActionLabel(ClassDescViewBased parent)
    {
        super(parent,"imeActionLabel");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        String convertedValue = getString(attr.getResourceDesc(),attrCtx.getXMLInflaterContext());

        TextView textView = (TextView)view;
        textView.setImeActionLabel(convertedValue,textView.getImeActionId());
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setAttributeToRemove(view, "",attrCtx); // No estoy seguro del valor por defecto
    }

}
