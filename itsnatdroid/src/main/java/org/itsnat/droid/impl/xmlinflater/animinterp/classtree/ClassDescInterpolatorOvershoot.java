package org.itsnat.droid.impl.xmlinflater.animinterp.classtree;

import android.content.Context;
import android.view.animation.OvershootInterpolator;

import org.itsnat.droid.impl.xmlinflater.animinterp.ClassDescInterpolatorMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetFloat;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescInterpolatorOvershoot extends ClassDescInterpolatorBased<OvershootInterpolator>
{
    public ClassDescInterpolatorOvershoot(ClassDescInterpolatorMgr classMgr)
    {
        super(classMgr, "overshootInterpolator", null);
    }

    @Override
    public Class<OvershootInterpolator> getDeclaredClass()
    {
        return OvershootInterpolator.class;
    }

    @Override
    protected OvershootInterpolator createResourceNative(Context ctx)
    {
        return new OvershootInterpolator(ctx,null);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecFieldSetFloat(this, "tension","mTension",2.0f));
    }
}

