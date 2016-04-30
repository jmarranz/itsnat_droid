package org.itsnat.droid.impl.xmlinflater.anim.classtree;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import org.itsnat.droid.impl.dom.anim.DOMElemAnimation;
import org.itsnat.droid.impl.xmlinflater.anim.AttrAnimationContext;
import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;

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
