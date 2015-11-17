package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.AttrDescViewReflecFieldSetBoolean;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ImageView_scaleType;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ImageView_tint;
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

    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescReflecMethodBoolean(this,"adjustViewBounds",false));
        addAttrDesc(new AttrDescReflecMethodDimensionIntFloor(this,"baseline",-1f));
        addAttrDesc(new AttrDescReflecMethodBoolean(this,"baselineAlignBottom",false));
        addAttrDesc(new AttrDescViewReflecFieldSetBoolean(this,"cropToPadding","mCropToPadding",false));
        addAttrDesc(new AttrDescReflecMethodDimensionIntRound(this,"maxHeight",(float)Integer.MAX_VALUE));
        addAttrDesc(new AttrDescReflecMethodDimensionIntRound(this,"maxWidth",(float)Integer.MAX_VALUE));
        addAttrDesc(new AttrDescView_widget_ImageView_scaleType(this));
        addAttrDesc(new AttrDescReflecMethodDrawable(this,"src","setImageDrawable","@null"));
        addAttrDesc(new AttrDescView_widget_ImageView_tint(this));
    }
}

