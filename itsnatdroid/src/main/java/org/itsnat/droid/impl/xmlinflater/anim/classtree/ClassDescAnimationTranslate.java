package org.itsnat.droid.impl.xmlinflater.anim.classtree;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import org.itsnat.droid.impl.dom.anim.DOMElemAnimation;
import org.itsnat.droid.impl.xmlinflater.anim.AttrAnimationContext;
import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.anim.attr.AttrDescAnimation_view_animation_ScaleAnimation_fromToXYScale;
import org.itsnat.droid.impl.xmlinflater.anim.attr.AttrDescAnimation_view_animation_ScaleAnimation_pivotXY;
import org.itsnat.droid.impl.xmlinflater.anim.attr.AttrDescAnimation_view_animation_TranslateAnimation_fromToXYDelta;

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

    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescAnimation_view_animation_TranslateAnimation_fromToXYDelta(this, "fromXDelta"));
        addAttrDescAN(new AttrDescAnimation_view_animation_TranslateAnimation_fromToXYDelta(this, "fromYDelta"));
        addAttrDescAN(new AttrDescAnimation_view_animation_TranslateAnimation_fromToXYDelta(this, "toXDelta"));
        addAttrDescAN(new AttrDescAnimation_view_animation_TranslateAnimation_fromToXYDelta(this, "toYDelta"));
    }

}
