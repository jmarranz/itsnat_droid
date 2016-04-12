package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.view.animation.Animation;
import android.widget.ViewAnimator;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_ViewAnimator_inoutAnimation extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    public AttrDescView_widget_ViewAnimator_inoutAnimation(ClassDescViewBased parent, String name)
    {
        super(parent,name);
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        Animation animation = getAnimation(attr.getResourceDesc(), attrCtx.getXMLInflaterContext());

        if ("inAnimation".equals(name))
            ((ViewAnimator)view).setInAnimation(animation);
        else if ("outAnimation".equals(name))
            ((ViewAnimator)view).setOutAnimation(animation);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "0", attrCtx);
    }

}
