package org.itsnat.droid.impl.xmlinflater.animator.attr;

import android.animation.Animator;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.animator.AttrAnimatorContext;
import org.itsnat.droid.impl.xmlinflater.animator.classtree.ClassDescAnimatorValue;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescAnimator_animation_Animator_repeatCount extends AttrDescReflecMethodInt<ClassDescAnimatorValue,Animator,AttrAnimatorContext>
{
    public AttrDescAnimator_animation_Animator_repeatCount(ClassDescAnimatorValue parent)
    {
        super(parent,"repeatCount",0);
    }

    @Override
    public void setAttribute(Animator animator, DOMAttr attr, AttrAnimatorContext attrCtx)
    {
        if ("infinite".equals(attr.getValue()))
            attr = DOMAttr.createDOMAttrCopy(attr, "-1");
        super.setAttribute(animator, attr, attrCtx);
    }

}
