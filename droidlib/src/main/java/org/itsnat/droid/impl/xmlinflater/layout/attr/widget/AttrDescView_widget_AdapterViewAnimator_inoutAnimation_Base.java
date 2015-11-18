package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.AdapterViewAnimator;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public abstract class AttrDescView_widget_AdapterViewAnimator_inoutAnimation_Base extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    protected static final int DEFAULT_ANIMATION_DURATION = 200;

    public AttrDescView_widget_AdapterViewAnimator_inoutAnimation_Base(ClassDescViewBased parent, String name)
    {
        super(parent,name);
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        int id = getIdentifier(attr.getValue(), attrCtx.getContext());

        ObjectAnimator animator = id > 0 ? (ObjectAnimator)AnimatorInflater.loadAnimator(attrCtx.getContext(), id) : getDefaultAnimation();

        setAnimation((AdapterViewAnimator)view,animator);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "0",attrCtx);
    }

    protected abstract void setAnimation(AdapterViewAnimator view,ObjectAnimator animator);
    protected abstract ObjectAnimator getDefaultAnimation();
}
