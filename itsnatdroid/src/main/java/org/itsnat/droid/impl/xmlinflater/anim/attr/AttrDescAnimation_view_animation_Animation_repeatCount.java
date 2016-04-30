package org.itsnat.droid.impl.xmlinflater.anim.attr;

import android.view.animation.Animation;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.anim.AttrAnimationContext;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimation;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescAnimation_view_animation_Animation_repeatCount extends AttrDescReflecMethodInt<ClassDescAnimation,Animation,AttrAnimationContext>
{
    public AttrDescAnimation_view_animation_Animation_repeatCount(ClassDescAnimation parent)
    {
        super(parent,"repeatCount",0);
    }

    @Override
    public void setAttribute(Animation animation, DOMAttr attr, AttrAnimationContext attrCtx)
    {
        if ("infinite".equals(attr.getValue()))
            attr = DOMAttr.createDOMAttrCopy(attr, "-1");
        super.setAttribute(animation, attr, attrCtx);
    }

}
