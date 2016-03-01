package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.ProgressBar;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_ProgressBar_interpolator extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_ProgressBar_interpolator(ClassDescViewBased parent)
    {
        super(parent,"interpolator");
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        int resId = getIdentifier(attr, attrCtx.getXMLInflaterLayout());

        ((ProgressBar)view).setInterpolator(attrCtx.getContext(), resId);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "@android:anim/linear_interpolator", attrCtx); // Yo creo que es el que usa por defecto Android en este caso
    }
}
