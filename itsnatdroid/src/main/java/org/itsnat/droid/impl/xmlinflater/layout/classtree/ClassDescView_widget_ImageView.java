package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ImageView_scaleType;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ImageView_tint;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntFloor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntRound;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_ImageView extends ClassDescViewBased
{
    public ClassDescView_widget_ImageView(ClassDescViewMgr classMgr,ClassDescView_view_View parentClass)
    {
        super(classMgr,"android.widget.ImageView",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "adjustViewBounds", false));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntFloor(this, "baseline", -1f));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "baselineAlignBottom", false));
        addAttrDescAN(new AttrDescReflecFieldSetBoolean(this, "cropToPadding", "mCropToPadding", false));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "maxHeight", (float) Integer.MAX_VALUE));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "maxWidth", (float) Integer.MAX_VALUE));
        addAttrDescAN(new AttrDescView_widget_ImageView_scaleType(this));
        addAttrDescAN(new AttrDescReflecMethodDrawable(this, "src", "setImageDrawable", "@null"));
        addAttrDescAN(new AttrDescView_widget_ImageView_tint(this));
        // android:tintMode es level 21
    }
}

