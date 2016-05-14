package org.itsnat.droid.impl.xmlinflater.animlayout.attr;

import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.animlayout.AttrLayoutAnimationContext;
import org.itsnat.droid.impl.xmlinflater.animlayout.classtree.ClassDescLayoutAnimation;
import org.itsnat.droid.impl.xmlinflater.animlayout.classtree.ClassDescLayoutAnimationBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescLayoutAnimation_LayoutAnimationController_animation extends AttrDesc<ClassDescLayoutAnimationBased,LayoutAnimationController,AttrLayoutAnimationContext>
{
    public AttrDescLayoutAnimation_LayoutAnimationController_animation(ClassDescLayoutAnimation parent)
    {
        super(parent,"animation");
    }

    @Override
    public void setAttribute(LayoutAnimationController layoutAnimation, DOMAttr attr, AttrLayoutAnimationContext attrCtx)
    {
        Animation animation = getAnimation(attr.getResourceDesc(), attrCtx.getXMLInflaterContext());

        layoutAnimation.setAnimation(animation);
    }

    @Override
    public void removeAttribute(LayoutAnimationController layoutAnimation, AttrLayoutAnimationContext attrCtx)
    {
        setAttributeToRemove(layoutAnimation, "@null", attrCtx);
    }

}
