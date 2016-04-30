package org.itsnat.droid.impl.xmlinflater.anim.classtree;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import org.itsnat.droid.impl.dom.anim.DOMElemAnimation;
import org.itsnat.droid.impl.xmlinflater.anim.AttrAnimationContext;
import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetFloat;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimationRotate extends ClassDescAnimationBased<RotateAnimation>
{
    public ClassDescAnimationRotate(ClassDescAnimationMgr classMgr, ClassDescAnimationBased<? super RotateAnimation> parentClass)
    {
        super(classMgr, "rotate", parentClass);
    }

    @Override
    public Class<RotateAnimation> getDeclaredClass()
    {
        return RotateAnimation.class;
    }

    @Override
    protected RotateAnimation createAnimationNative(Context ctx)
    {
        return new RotateAnimation(ctx,null);
    }

    @Override
    protected boolean isAttributeIgnored(String namespaceURI,String name)
    {
        return false;
    }


    //@SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecFieldSetFloat(this, "fromDegrees", "mFromDegrees",0.0f));
        addAttrDescAN(new AttrDescReflecFieldSetFloat(this, "toDegrees", "mToDegrees",0.0f));

/*

57
58         Description d = Description.parseValue(a.peekValue(
59             com.android.internal.R.styleable.RotateAnimation_pivotX));
60         mPivotXType = d.type;
61         mPivotXValue = d.value;
62
63         d = Description.parseValue(a.peekValue(
64             com.android.internal.R.styleable.RotateAnimation_pivotY));
65         mPivotYType = d.type;
66         mPivotYValue = d.value;
67

        addAttrDescAN(new AttrDescReflecFieldSetFloat(this, "fromAlpha", "mFromAlpha",1.0f));
        addAttrDescAN(new AttrDescReflecFieldSetFloat(this, "toAlpha", "mToAlpha",1.0f));
         */


    }

}
