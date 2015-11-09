package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.content.Context;
import android.util.TypedValue;
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
public class AttrDescView_widget_TextView_textSize extends AttrDescView
{
    public AttrDescView_widget_TextView_textSize(ClassDescViewBased parent)
    {
        super(parent,"textSize");
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        TextView textView = (TextView)view;

        float value = getDimensionFloatRound(attr.getValue(), attrCtx.getContext());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,value);
    }

    public void removeAttribute(View view, XMLInflaterLayout xmlInflaterLayout, Context ctx)
    {
        // No se que hacer, poner a cero el texto no tiene sentido, se tendría que extraer del Theme actual, un follón y total será muy raro
    }
}
