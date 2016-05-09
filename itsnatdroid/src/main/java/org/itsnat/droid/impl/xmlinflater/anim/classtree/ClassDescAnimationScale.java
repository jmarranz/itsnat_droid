package org.itsnat.droid.impl.xmlinflater.anim.classtree;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.anim.attr.AttrDescAnimation_view_animation_ScaleAnimation_fromToXYScale;
import org.itsnat.droid.impl.xmlinflater.anim.attr.AttrDescAnimation_view_animation_ScaleAnimation_pivotXY;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimationScale extends ClassDescAnimationBased<ScaleAnimation>
{
    public ClassDescAnimationScale(ClassDescAnimationMgr classMgr, ClassDescAnimationBased<Animation> parentClass)
    {
        super(classMgr, "scale", parentClass);
    }

    @Override
    public Class<ScaleAnimation> getDeclaredClass()
    {
        return ScaleAnimation.class;
    }

    @Override
    protected ScaleAnimation createResourceNative(Context ctx)
    {
        return new ScaleAnimation(ctx,null);
    }

    @Override
    protected boolean isAttributeIgnored(String namespaceURI,String name)
    {
        return false;
    }

    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescAnimation_view_animation_ScaleAnimation_fromToXYScale(this, "fromXScale"));
        addAttrDescAN(new AttrDescAnimation_view_animation_ScaleAnimation_fromToXYScale(this, "fromYScale"));
        addAttrDescAN(new AttrDescAnimation_view_animation_ScaleAnimation_fromToXYScale(this, "toXScale"));
        addAttrDescAN(new AttrDescAnimation_view_animation_ScaleAnimation_fromToXYScale(this, "toYScale"));
        addAttrDescAN(new AttrDescAnimation_view_animation_ScaleAnimation_pivotXY(this, "pivotX"));
        addAttrDescAN(new AttrDescAnimation_view_animation_ScaleAnimation_pivotXY(this, "pivotY"));

    }

}
