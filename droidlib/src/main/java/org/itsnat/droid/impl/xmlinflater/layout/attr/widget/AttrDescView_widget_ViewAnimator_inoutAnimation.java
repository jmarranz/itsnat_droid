package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.content.Context;
import android.view.View;
import android.widget.ViewAnimator;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_ViewAnimator_inoutAnimation extends AttrDescView
{
    public AttrDescView_widget_ViewAnimator_inoutAnimation(ClassDescViewBased parent, String name)
    {
        super(parent,name);
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        Context ctx = attrCtx.getContext();
        int id = getIdentifier(attr.getValue(),ctx);

        if ("inAnimation".equals(name))
            ((ViewAnimator)view).setInAnimation(ctx,id);
        else if ("outAnimation".equals(name))
            ((ViewAnimator)view).setOutAnimation(ctx,id);
    }

    public void removeAttribute(View view, XMLInflaterLayout xmlInflaterLayout, Context ctx)
    {
        setToRemoveAttribute(view, "0", xmlInflaterLayout, ctx);
    }

}
