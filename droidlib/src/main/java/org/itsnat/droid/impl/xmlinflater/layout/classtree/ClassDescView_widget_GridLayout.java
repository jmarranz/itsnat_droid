package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.OrientationUtil;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_GridLayout_alignmentMode;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_GridLayout extends ClassDescViewBased
{
    public ClassDescView_widget_GridLayout(ClassDescViewMgr classMgr,ClassDescView_view_ViewGroup parentClass)
    {
        super(classMgr,"android.widget.GridLayout",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescView_widget_GridLayout_alignmentMode(this));
        addAttrDescAN(new AttrDescReflecMethodInt(this, "columnCount", Integer.MIN_VALUE)); // El MIN_VALUE está sacado del código fuente
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "columnOrderPreserved", true));
        addAttrDescAN(new AttrDescReflecMethodNameSingle(this, "orientation", int.class, OrientationUtil.valueMap, "horizontal"));

        addAttrDescAN(new AttrDescReflecMethodInt(this, "rowCount", Integer.MIN_VALUE)); // El MIN_VALUE está sacado del código fuente
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "rowOrderPreserved", true));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "useDefaultMargins", false));

    }
}

