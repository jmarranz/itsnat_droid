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
public class AttrDescView_widget_TextView_lineSpacingMultiplier extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    protected FieldContainer<Float> field;

    public AttrDescView_widget_TextView_lineSpacingMultiplier(ClassDescViewBased parent)
    {
        super(parent,"lineSpacingMultiplier");
        this.field = new FieldContainer<Float>(parent.getDeclaredClass(),"mSpacingAdd");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        float convertedValue = getFloat(attr.getResourceDesc(),attrCtx.getXMLInflaterContext());

        TextView textView = (TextView)view;
        textView.setLineSpacing(getLineSpacingExtra(textView),convertedValue);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "1.0", attrCtx);
    }

    protected float getLineSpacingExtra(TextView view)
    {
        return field.get(view);
    }
}
