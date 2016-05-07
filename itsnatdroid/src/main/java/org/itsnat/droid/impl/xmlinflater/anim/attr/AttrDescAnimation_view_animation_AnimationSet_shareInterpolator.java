package org.itsnat.droid.impl.xmlinflater.anim.attr;

import android.view.animation.AnimationSet;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.anim.AttrAnimationContext;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimationSet;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescAnimation_view_animation_AnimationSet_shareInterpolator extends AttrDesc<ClassDescAnimationSet,AnimationSet,AttrAnimationContext>
{
    private static final int PROPERTY_SHARE_INTERPOLATOR_MASK = 0x10;

    protected MethodContainer<Integer> methodSetFlag;

    public AttrDescAnimation_view_animation_AnimationSet_shareInterpolator(ClassDescAnimationSet parent)
    {
        super(parent,"shareInterpolator");

        this.methodSetFlag = new MethodContainer<Integer>(AnimationSet.class,"setFlag",new Class[] {int.class, boolean.class});
    }

    @Override
    public void setAttribute(AnimationSet animationSet, DOMAttr attr, AttrAnimationContext attrCtx)
    {
        boolean value = getBoolean(attr.getResourceDesc(), attrCtx.getXMLInflaterContext());
        methodSetFlag.invoke(animationSet, PROPERTY_SHARE_INTERPOLATOR_MASK,value);
    }

    @Override
    public void removeAttribute(AnimationSet animationSet, AttrAnimationContext attrCtx)
    {
        setAttributeToRemove(animationSet, "true", attrCtx);
    }
}
