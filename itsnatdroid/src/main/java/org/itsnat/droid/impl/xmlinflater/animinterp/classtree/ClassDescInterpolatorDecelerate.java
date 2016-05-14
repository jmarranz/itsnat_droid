package org.itsnat.droid.impl.xmlinflater.animinterp.classtree;

import android.content.Context;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import org.itsnat.droid.impl.xmlinflater.animinterp.ClassDescInterpolatorMgr;
import org.itsnat.droid.impl.xmlinflater.animinterp.attr.AttrDescInterpolator_AccelerateInterpolator_factor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSet;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetFloat;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescInterpolatorDecelerate extends ClassDescInterpolatorBased<DecelerateInterpolator>
{
    public ClassDescInterpolatorDecelerate(ClassDescInterpolatorMgr classMgr)
    {
        super(classMgr, "decelerateInterpolator", null);
    }

    @Override
    public Class<DecelerateInterpolator> getDeclaredClass()
    {
        return DecelerateInterpolator.class;
    }

    @Override
    protected DecelerateInterpolator createResourceNative(Context ctx)
    {
        return new DecelerateInterpolator(ctx,null);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecFieldSetFloat(this, "factor","mFactor",1.0f));
    }
}

