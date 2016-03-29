package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.AdapterViewAnimator;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_AdapterViewAnimator_inoutAnimation extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    protected static final int DEFAULT_ANIMATION_DURATION = 200; // Copiado del código fuente

    public AttrDescView_widget_AdapterViewAnimator_inoutAnimation(ClassDescViewBased parent, String name)
    {
        super(parent,name);
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        ObjectAnimator animator = (ObjectAnimator) getAnimator(attr, attrCtx.getXMLInflaterLayout());

        AdapterViewAnimator adapterViewAnimator = (AdapterViewAnimator)view;

        String value = attr.getName();
        if ("inAnimation".equals(value))
        {
            if (animator == null) animator = getDefaultInAnimation();
            adapterViewAnimator.setInAnimation(animator);
        }
        else if ("outAnimation".equals(value))
        {
            if (animator == null) animator = getDefaultOutAnimation();
            adapterViewAnimator.setOutAnimation(animator);
        }
        else throw MiscUtil.internalError();
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "0", attrCtx);
    }

    protected static ObjectAnimator getDefaultInAnimation()
    {
        // Copiado del código fuente
        ObjectAnimator anim = ObjectAnimator.ofFloat(null, "alpha", 0.0f, 1.0f);
        anim.setDuration(DEFAULT_ANIMATION_DURATION);
        return anim;
    }

    protected static ObjectAnimator getDefaultOutAnimation()
    {
        // Copiado del código fuente
        ObjectAnimator anim = ObjectAnimator.ofFloat(null, "alpha", 1.0f, 0.0f);
        anim.setDuration(DEFAULT_ANIMATION_DURATION);
        return anim;
    }
}
