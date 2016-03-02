package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_textSize extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_TextView_textSize(ClassDescViewBased parent)
    {
        super(parent,"textSize");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        TextView textView = (TextView)view;

        float value = getDimensionFloatRound(attr, attrCtx.getXMLInflaterLayout());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,value);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        // No se que hacer, poner a cero el texto no tiene sentido, se tendría que extraer del Theme actual, un follón y total será muy raro
    }
}
