package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_fontFamily extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_TextView_fontFamily(ClassDescViewBased parent)
    {
        super(parent,"fontFamily");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        String familyName = getString(attr.getResourceDesc(),attrCtx.getXMLInflaterContext());

        TextView textView = (TextView)view;
        Typeface currentTF = textView.getTypeface();
        Typeface tf;
        if (currentTF !=null)
            tf = Typeface.create(familyName,currentTF.getStyle());
        else
            tf = Typeface.create(familyName,Typeface.NORMAL);

        textView.setTypeface(tf);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        // No se como poner el fontFamily por defecto
        //setAttributeToRemove(view, "vertical",attrCtx);
    }
}
