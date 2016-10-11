package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ListViewAndAbsSpinner_entries;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntRound;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_ListView extends ClassDescViewBased
{
    public ClassDescView_widget_ListView(ClassDescViewMgr classMgr,ClassDescView_widget_AbsListView parentClass)
    {
        super(classMgr,"android.widget.ListView",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodDrawable(this, "divider", "@null")); // Android tiene un drawable por defecto
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "dividerHeight", 0f));
        addAttrDescAN(new AttrDescView_widget_ListViewAndAbsSpinner_entries(this));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "footerDividersEnabled", true));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "headerDividersEnabled", true));
    }
}

