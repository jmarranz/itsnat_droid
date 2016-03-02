package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_DatePicker_endYear_startYear;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_DatePicker_maxDate_minDate;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_DatePicker extends ClassDescViewBased
{
    public ClassDescView_widget_DatePicker(ClassDescViewMgr classMgr,ClassDescView_widget_FrameLayout parentClass)
    {
        super(classMgr,"android.widget.DatePicker",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();


        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "calendarViewShown", true));
        addAttrDescAN(new AttrDescView_widget_DatePicker_endYear_startYear(this, "endYear"));
        addAttrDescAN(new AttrDescView_widget_DatePicker_maxDate_minDate(this, "maxDate"));
        addAttrDescAN(new AttrDescView_widget_DatePicker_maxDate_minDate(this, "minDate"));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "spinnersShown", true));
        addAttrDescAN(new AttrDescView_widget_DatePicker_endYear_startYear(this, "startYear"));
    }
}

