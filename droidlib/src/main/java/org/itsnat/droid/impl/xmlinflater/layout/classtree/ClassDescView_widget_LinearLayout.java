package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.OrientationUtil;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_LinearLayout_baselineAlignedChildIndex;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_LinearLayout_showDividers;
import org.itsnat.droid.impl.xmlinflater.shared.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntRound;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodFloat;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameMultiple;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_LinearLayout extends ClassDescViewBased
{
    public ClassDescView_widget_LinearLayout(ClassDescViewMgr classMgr,ClassDescView_view_ViewGroup parentClass)
    {
        super(classMgr,"android.widget.LinearLayout",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "baselineAligned", true));
        addAttrDescAN(new AttrDescView_widget_LinearLayout_baselineAlignedChildIndex(this));
        addAttrDescAN(new AttrDescReflecMethodDrawable(this, "divider", "setDividerDrawable", null)); // Hay un drawable por defecto de Android
        // showDividers y dividerPadding atributos los he descubierto por casualidad en StackOverflow y resulta que son atributos NO documentados de LinearLayout (se ven en el código fuente)
        addAttrDescAN(new AttrDescView_widget_LinearLayout_showDividers(this));  // showDividers
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "dividerPadding", 0f));
        addAttrDescAN(new AttrDescReflecMethodNameMultiple(this, "gravity", GravityUtil.valueMap, "start|top"));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "measureWithLargestChild", "setMeasureWithLargestChildEnabled", false));
        addAttrDescAN(new AttrDescReflecMethodNameSingle(this, "orientation", int.class, OrientationUtil.valueMap, "horizontal"));
        addAttrDescAN(new AttrDescReflecMethodFloat(this, "weightSum", -1.0f));
    }
}

