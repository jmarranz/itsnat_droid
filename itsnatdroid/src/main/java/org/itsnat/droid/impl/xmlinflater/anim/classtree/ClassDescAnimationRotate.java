package org.itsnat.droid.impl.xmlinflater.anim.classtree;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.anim.attr.AttrDescAnimation_view_animation_RotateAnimation_pivotXY;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetFloat;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimationRotate extends ClassDescAnimationBased<RotateAnimation>
{
    public ClassDescAnimationRotate(ClassDescAnimationMgr classMgr, ClassDescAnimationBased<Animation> parentClass)
    {
        super(classMgr, "rotate", parentClass);
    }

    @Override
    public Class<RotateAnimation> getDeclaredClass()
    {
        return RotateAnimation.class;
    }

    @Override
    protected RotateAnimation createResourceNative(Context ctx)
    {
        return new RotateAnimation(ctx,null);
    }

    @Override
    protected boolean isAttributeIgnored(String namespaceURI,String name)
    {
        return false;
    }


    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecFieldSetFloat(this, "fromDegrees", "mFromDegrees",0.0f));
        addAttrDescAN(new AttrDescReflecFieldSetFloat(this, "toDegrees", "mToDegrees",0.0f));

        addAttrDescAN(new AttrDescAnimation_view_animation_RotateAnimation_pivotXY(this, "pivotX"));
        addAttrDescAN(new AttrDescAnimation_view_animation_RotateAnimation_pivotXY(this, "pivotY"));

    }

}
