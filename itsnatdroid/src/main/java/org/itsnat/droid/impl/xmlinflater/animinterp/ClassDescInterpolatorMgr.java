package org.itsnat.droid.impl.xmlinflater.animinterp;

import org.itsnat.droid.impl.xmlinflater.ClassDescMgr;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.animinterp.classtree.ClassDescInterpolatorAccelerate;
import org.itsnat.droid.impl.xmlinflater.animinterp.classtree.ClassDescInterpolatorAnticipate;
import org.itsnat.droid.impl.xmlinflater.animinterp.classtree.ClassDescInterpolatorAnticipateOvershoot;
import org.itsnat.droid.impl.xmlinflater.animinterp.classtree.ClassDescInterpolatorBased;
import org.itsnat.droid.impl.xmlinflater.animinterp.classtree.ClassDescInterpolatorCycle;
import org.itsnat.droid.impl.xmlinflater.animinterp.classtree.ClassDescInterpolatorDecelerate;
import org.itsnat.droid.impl.xmlinflater.animinterp.classtree.ClassDescInterpolatorNoField;
import org.itsnat.droid.impl.xmlinflater.animinterp.classtree.ClassDescInterpolatorOvershoot;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescInterpolatorMgr extends ClassDescMgr<ClassDescInterpolatorBased>
{
    public ClassDescInterpolatorMgr(XMLInflaterRegistry parent)
    {
        super(parent);
        initClassDesc();
    }

    @Override
    public ClassDescInterpolatorBased get(String resourceTypeName)
    {
        return classes.get(resourceTypeName);
    }


    @Override
    protected void initClassDesc()
    {
        // http://developer.android.com/guide/topics/resources/animation-resource.html#Interpolators
        ClassDescInterpolatorAccelerate accelerate = new ClassDescInterpolatorAccelerate(this);
        addClassDesc(accelerate);

        ClassDescInterpolatorDecelerate decelerate = new ClassDescInterpolatorDecelerate(this);
        addClassDesc(decelerate);

        ClassDescInterpolatorCycle cycle = new ClassDescInterpolatorCycle(this);
        addClassDesc(cycle);

        ClassDescInterpolatorAnticipate anticipate = new ClassDescInterpolatorAnticipate(this);
        addClassDesc(anticipate);

        ClassDescInterpolatorOvershoot overshoot = new ClassDescInterpolatorOvershoot(this);
        addClassDesc(overshoot);

        ClassDescInterpolatorAnticipateOvershoot anticipOvershoot = new ClassDescInterpolatorAnticipateOvershoot(this);
        addClassDesc(anticipOvershoot);

        // Sin fields
        ClassDescInterpolatorNoField accDec = new ClassDescInterpolatorNoField(this,"accelerateDecelerateInterpolator");
        addClassDesc(accDec);
        ClassDescInterpolatorNoField bounce = new ClassDescInterpolatorNoField(this,"bounceInterpolator");
        addClassDesc(bounce);
        ClassDescInterpolatorNoField linear = new ClassDescInterpolatorNoField(this,"linearInterpolator");
        addClassDesc(linear);
    }

}
