package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_lineSpacingExtra extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    protected FieldContainer<Float> field;

    public AttrDescView_widget_TextView_lineSpacingExtra(ClassDescViewBased parent)
    {
        super(parent,"lineSpacingExtra");
        this.field = new FieldContainer<Float>(parent.getDeclaredClass(),"mSpacingMult");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        float convertedValue = getDimensionFloatRound(attr.getResourceDesc(),attrCtx.getXMLInflaterContext());

        TextView textView = (TextView)view;
        textView.setLineSpacing(convertedValue, getMultiplier(textView));
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setAttributeToRemove(view, "0dp", attrCtx);
    }

    protected float getMultiplier(TextView view)
    {
        return field.get(view);
    }
}
