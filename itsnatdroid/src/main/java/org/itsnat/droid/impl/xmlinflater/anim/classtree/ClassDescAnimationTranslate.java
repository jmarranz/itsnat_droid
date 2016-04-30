package org.itsnat.droid.impl.xmlinflater.anim.classtree;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import org.itsnat.droid.impl.dom.anim.DOMElemAnimation;
import org.itsnat.droid.impl.xmlinflater.anim.AttrAnimationContext;
import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimationTranslate extends ClassDescAnimationBased<TranslateAnimation>
{
    public ClassDescAnimationTranslate(ClassDescAnimationMgr classMgr, ClassDescAnimationBased<? super TranslateAnimation> parentClass)
    {
        super(classMgr, "translate", parentClass);
    }

    @Override
    public Class<TranslateAnimation> getDeclaredClass()
    {
        return TranslateAnimation.class;
    }

    @Override
    protected TranslateAnimation createAnimationNative(Context ctx)
    {
        return new TranslateAnimation(ctx,null);
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
