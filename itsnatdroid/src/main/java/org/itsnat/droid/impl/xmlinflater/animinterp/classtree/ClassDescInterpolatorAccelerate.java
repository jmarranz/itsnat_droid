package org.itsnat.droid.impl.xmlinflater.animinterp.classtree;

import android.content.Context;
import android.view.animation.AccelerateInterpolator;

import org.itsnat.droid.impl.xmlinflater.animinterp.ClassDescInterpolatorMgr;
import org.itsnat.droid.impl.xmlinflater.animinterp.attr.AttrDescInterpolator_AccelerateInterpolator_factor;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescInterpolatorAccelerate extends ClassDescInterpolatorBased<AccelerateInterpolator>
{
    public ClassDescInterpolatorAccelerate(ClassDescInterpolatorMgr classMgr)
    {
        super(classMgr, "accelerateInterpolator", null);
    }

    @Override
    public Class<AccelerateInterpolator> getDeclaredClass()
    {
        return AccelerateInterpolator.class;
    }

    @Override
    protected AccelerateInterpolator createResourceNative(Context ctx)
    {
        return new AccelerateInterpolator(ctx,null);
    }

    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescInterpolator_AccelerateInterpolator_factor(this, "factor"));

    }
}

