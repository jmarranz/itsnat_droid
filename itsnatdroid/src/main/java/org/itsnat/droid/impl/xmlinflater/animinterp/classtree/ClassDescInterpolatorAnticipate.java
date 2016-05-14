package org.itsnat.droid.impl.xmlinflater.animinterp.classtree;

import android.content.Context;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;

import org.itsnat.droid.impl.xmlinflater.animinterp.ClassDescInterpolatorMgr;
import org.itsnat.droid.impl.xmlinflater.animinterp.attr.AttrDescInterpolator_AccelerateInterpolator_factor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetFloat;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescInterpolatorAnticipate extends ClassDescInterpolatorBased<AnticipateInterpolator>
{
    public ClassDescInterpolatorAnticipate(ClassDescInterpolatorMgr classMgr)
    {
        super(classMgr, "anticipateInterpolator", null);
    }

    @Override
    public Class<AnticipateInterpolator> getDeclaredClass()
    {
        return AnticipateInterpolator.class;
    }

    @Override
    protected AnticipateInterpolator createResourceNative(Context ctx)
    {
        return new AnticipateInterpolator(ctx,null);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecFieldSetFloat(this, "tension","mTension",2.0f));

    }
}

