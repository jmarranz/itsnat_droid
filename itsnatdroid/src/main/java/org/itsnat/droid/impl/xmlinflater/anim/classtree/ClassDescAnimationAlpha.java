package org.itsnat.droid.impl.xmlinflater.anim.classtree;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttributeMap;
import org.itsnat.droid.impl.dom.anim.DOMElemAnimation;
import org.itsnat.droid.impl.dom.animator.DOMElemAnimator;
import org.itsnat.droid.impl.util.MapSmart;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.anim.AttrAnimationContext;
import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.animator.AttrAnimatorContext;
import org.itsnat.droid.impl.xmlinflater.animator.ClassDescAnimatorMgr;
import org.itsnat.droid.impl.xmlinflater.animator.XMLInflaterAnimator;
import org.itsnat.droid.impl.xmlinflater.animator.classtree.ClassDescAnimatorBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimationAlpha extends ClassDescAnimationBased<AlphaAnimation>
{
    public ClassDescAnimationAlpha(ClassDescAnimationMgr classMgr, ClassDescAnimationBased<? super AlphaAnimation> parentClass)
    {
        super(classMgr, "alpha", parentClass);
    }

    @Override
    public Class<AlphaAnimation> getDeclaredClass()
    {
        return AlphaAnimation.class;
    }

    @Override
    protected AlphaAnimation createAnimationNative(Context ctx)
    {
        return new AlphaAnimation(ctx,null);
    }

    @Override
    protected boolean isAttributeIgnored(String namespaceURI,String name)
    {
        return false;
    }

    protected void fillAnimationAttributes(Animation animation, DOMElemAnimation domElement, AttrAnimationContext attrCtx)
    {
        fillAnimationValueConstructionAttributes(animation, domElement, attrCtx);

        super.fillAnimationAttributes(animation, domElement,attrCtx);
    }

    protected void fillAnimationValueConstructionAttributes(Animation animation, DOMElemAnimation domElement, AttrAnimationContext attrCtx)
    {
        // ???????
    }

    protected void init()
    {
        super.init();

        //addAttrDescAN(new AttrDescReflecMethodInt(this, "repeatCount", 0)); // Se puede llamar independientemente de valueFrom y valueTo
        //addAttrDescAN(new AttrDescReflecMethodNameSingle(this, "repeatMode", int.class, repeatModeMap, "restart"));  // "
    }

}
