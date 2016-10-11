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

        // android:internalLayout no cuenta pues es de uso interno (existe en level 16) NO documentado;

        // android:calendarTextColor documentado pero NO implementado
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "calendarViewShown", true));
        // android:datePickerMode es level 21
        // android:dayOfWeekBackground documentado pero NO implementado
        // android:dayOfWeekTextAppearance documentado pero NO implementado
        addAttrDescAN(new AttrDescView_widget_DatePicker_endYear_startYear(this, "endYear"));
        // android:firstDayOfWeek es level 21
        // android:headerBackground documentado pero NO implementado
        // android:headerDayOfMonthTextAppearance documentado pero NO implementado
        // android:headerMonthTextAppearance documentado pero NO implementado
        // android:headerYearTextAppearance documentado pero NO implementado
        addAttrDescAN(new AttrDescView_widget_DatePicker_maxDate_minDate(this, "maxDate"));
        addAttrDescAN(new AttrDescView_widget_DatePicker_maxDate_minDate(this, "minDate"));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "spinnersShown", true));
        addAttrDescAN(new AttrDescView_widget_DatePicker_endYear_startYear(this, "startYear"));
        // android:yearListItemTextAppearance documentado pero NO implementado
        // android:yearListSelectorColor documentado pero NO implementado
    }
}

