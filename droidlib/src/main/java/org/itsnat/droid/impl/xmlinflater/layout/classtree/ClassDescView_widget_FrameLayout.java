package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import android.os.Build;

import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescViewReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescViewReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescViewReflecMethodMultipleName;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_FrameLayout extends ClassDescViewBased
{
    public ClassDescView_widget_FrameLayout(ClassDescViewMgr classMgr,ClassDescView_view_ViewGroup parentClass)
    {
        super(classMgr,"android.widget.FrameLayout",parentClass);
    }

    protected void init()
    {
        super.init();

        if (Build.VERSION.SDK_INT < MiscUtil.MARSHMALLOW) // < 23
        {
            addAttrDesc(new AttrDescViewReflecMethodDrawable(this, "foreground", "@null")); // A partir de MARSHMALLOW se define en View
        }
        addAttrDesc(new AttrDescViewReflecMethodMultipleName(this,"foregroundGravity", GravityUtil.valueMap,"fill"));
        addAttrDesc(new AttrDescViewReflecMethodBoolean(this, "measureAllChildren", false));

    }
}

