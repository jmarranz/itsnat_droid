package org.itsnat.droid.impl.xmlinflater.animinterp.classtree;

import android.content.Context;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.CycleInterpolator;

import org.itsnat.droid.impl.xmlinflater.animinterp.ClassDescInterpolatorMgr;
import org.itsnat.droid.impl.xmlinflater.animinterp.attr.AttrDescInterpolator_AccelerateInterpolator_factor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetFloat;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescInterpolatorCycle extends ClassDescInterpolatorBased<CycleInterpolator>
{
    public ClassDescInterpolatorCycle(ClassDescInterpolatorMgr classMgr)
    {
        super(classMgr, "cycleInterpolator", null);
    }

    @Override
    public Class<CycleInterpolator> getDeclaredClass()
    {
        return CycleInterpolator.class;
    }

    @Override
    protected CycleInterpolator createResourceNative(Context ctx)
    {
        return new CycleInterpolator(ctx,null);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecFieldSetFloat(this, "cycles","mCycles",1.0f));
    }
}

