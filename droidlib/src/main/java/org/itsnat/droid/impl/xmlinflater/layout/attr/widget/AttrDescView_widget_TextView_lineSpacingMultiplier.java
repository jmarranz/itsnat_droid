package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_lineSpacingMultiplier extends AttrDescView
{
    protected FieldContainer<Float> field;

    public AttrDescView_widget_TextView_lineSpacingMultiplier(ClassDescViewBased parent)
    {
        super(parent,"lineSpacingMultiplier");
        this.field = new FieldContainer<Float>(parent.getDeclaredClass(),"mSpacingAdd");
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        float convertedValue = getFloat(attr.getValue(),attrCtx.getContext());

        TextView textView = (TextView)view;
        textView.setLineSpacing(getLineSpacingExtra(textView),convertedValue);
    }

    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "1.0", attrCtx);
    }

    protected float getLineSpacingExtra(TextView view)
    {
        return field.get(view);
    }
}
