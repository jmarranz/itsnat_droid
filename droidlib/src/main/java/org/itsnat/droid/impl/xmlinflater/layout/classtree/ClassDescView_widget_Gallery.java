package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.shared.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntFloor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodFloat;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameMultiple;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_Gallery extends ClassDescViewBased
{
    public ClassDescView_widget_Gallery(ClassDescViewMgr classMgr,ClassDescView_widget_AbsSpinner parentClass)
    {
        super(classMgr,"android.widget.Gallery",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodInt(this, "animationDuration", 400));
        addAttrDescAN(new AttrDescReflecMethodNameMultiple(this, "gravity", GravityUtil.valueMap, "left")); // No está claro que left sea el valor por defecto
        addAttrDescAN(new AttrDescReflecMethodDimensionIntFloor(this, "spacing", 0f));
        addAttrDescAN(new AttrDescReflecMethodFloat(this, "unselectedAlpha", 0.5f));


    }
}

