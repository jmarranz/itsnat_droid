package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.OrientationUtil;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_GridLayout_alignmentMode;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodSingleName;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_GridLayout extends ClassDescViewBased
{
    public ClassDescView_widget_GridLayout(ClassDescViewMgr classMgr,ClassDescView_view_ViewGroup parentClass)
    {
        super(classMgr,"android.widget.GridLayout",parentClass);
    }

    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescView_widget_GridLayout_alignmentMode(this));
        addAttrDesc(new AttrDescReflecMethodInt(this,"columnCount",Integer.MIN_VALUE)); // El MIN_VALUE está sacado del código fuente
        addAttrDesc(new AttrDescReflecMethodBoolean(this,"columnOrderPreserved",true));
        addAttrDesc(new AttrDescReflecMethodSingleName(this,"orientation",int.class, OrientationUtil.valueMap,"horizontal"));

        addAttrDesc(new AttrDescReflecMethodInt(this,"rowCount",Integer.MIN_VALUE)); // El MIN_VALUE está sacado del código fuente
        addAttrDesc(new AttrDescReflecMethodBoolean(this,"rowOrderPreserved",true));
        addAttrDesc(new AttrDescReflecMethodBoolean(this,"useDefaultMargins",false));

    }
}

