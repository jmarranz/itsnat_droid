package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntFloor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodFloat;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodMultipleName;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_Gallery extends ClassDescViewBased
{
    public ClassDescView_widget_Gallery(ClassDescViewMgr classMgr,ClassDescView_widget_AbsSpinner parentClass)
    {
        super(classMgr,"android.widget.Gallery",parentClass);
    }

    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescReflecMethodInt(this,"animationDuration",400));
        addAttrDesc(new AttrDescReflecMethodMultipleName(this,"gravity", GravityUtil.valueMap,"left")); // No está claro que left sea el valor por defecto
        addAttrDesc(new AttrDescReflecMethodDimensionIntFloor(this,"spacing",0f));
        addAttrDesc(new AttrDescReflecMethodFloat(this,"unselectedAlpha",0.5f));


    }
}

