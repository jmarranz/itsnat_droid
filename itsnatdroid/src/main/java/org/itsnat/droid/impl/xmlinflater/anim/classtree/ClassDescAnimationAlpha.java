package org.itsnat.droid.impl.xmlinflater.anim.classtree;

import android.content.Context;
import android.view.animation.AlphaAnimation;

import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetFloat;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimationAlpha extends ClassDescAnimationBased<AlphaAnimation>
{
    public ClassDescAnimationAlpha(ClassDescAnimationMgr classMgr, ClassDescAnimation parentClass)
    {
        super(classMgr, "alpha", parentClass);
    }

    @Override
    public Class<AlphaAnimation> getDeclaredClass()
    {
        return AlphaAnimation.class;
    }

    @Override
    protected AlphaAnimation createResourceNative(Context ctx)
    {
        return new AlphaAnimation(ctx,null);
    }

    @Override
    public boolean isAttributeIgnored(AlphaAnimation resource,String namespaceURI,String name)
    {
        return false;
    }


    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecFieldSetFloat(this, "fromAlpha", "mFromAlpha",1.0f));
        addAttrDescAN(new AttrDescReflecFieldSetFloat(this, "toAlpha", "mToAlpha",1.0f));
    }

}
