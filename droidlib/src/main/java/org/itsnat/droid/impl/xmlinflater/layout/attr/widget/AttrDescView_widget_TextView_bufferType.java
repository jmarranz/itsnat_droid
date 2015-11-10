package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_bufferType extends AttrDescView
{
    public static final MapSmart<String,TextView.BufferType> valueMap = MapSmart.<String,TextView.BufferType>create( 3 );
    static
    {
        valueMap.put("normal", TextView.BufferType.NORMAL);
        valueMap.put("spannable", TextView.BufferType.SPANNABLE);
        valueMap.put("editable", TextView.BufferType.EDITABLE);
    }

    public AttrDescView_widget_TextView_bufferType(ClassDescViewBased parent)
    {
        super(parent,"bufferType");
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        TextView.BufferType convertedValue = AttrDescView.<TextView.BufferType>parseSingleName(attr.getValue(), valueMap);

        TextView textView = (TextView)view;
        textView.setText(textView.getText(),(TextView.BufferType)convertedValue);
    }

    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "normal",attrCtx);
    }

}
