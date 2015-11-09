package org.itsnat.droid.impl.xmlinflater.layout.attr.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_view_ViewGroup_layoutAnimation extends AttrDescView
{
    public AttrDescView_view_ViewGroup_layoutAnimation(ClassDescViewBased parent)
    {
        super(parent,"layoutAnimation");
    }

    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        int id = getIdentifier(attr.getValue(), attrCtx.getContext());

        LayoutAnimationController controller = id > 0 ?  AnimationUtils.loadLayoutAnimation(attrCtx.getContext(), id) : null;

        ((ViewGroup)view).setLayoutAnimation(controller);
    }

    public void removeAttribute(View view, XMLInflaterLayout xmlInflaterLayout, Context ctx)
    {
        setToRemoveAttribute(view, "-1", xmlInflaterLayout, ctx);
    }
}
