package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.ProgressBar;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_ProgressBar_interpolator extends AttrDescView
{
    public AttrDescView_widget_ProgressBar_interpolator(ClassDescViewBased parent)
    {
        super(parent,"interpolator");
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        int resId = getIdentifier(attr.getValue(),attrCtx.getContext());

        ((ProgressBar)view).setInterpolator(attrCtx.getContext(), resId);
    }

    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "@android:anim/linear_interpolator", attrCtx); // Yo creo que es el que usa por defecto Android en este caso
    }
}
