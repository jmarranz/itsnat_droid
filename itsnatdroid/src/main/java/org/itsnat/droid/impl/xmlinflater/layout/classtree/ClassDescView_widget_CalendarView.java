package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewPostCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_CalendarView_dateTextAppearance;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_CalendarView_maxDate_minDate;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_CalendarView_weekDayTextAppearance;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodColor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_CalendarView extends ClassDescViewBased
{
    public ClassDescView_widget_CalendarView(ClassDescViewMgr classMgr,ClassDescView_widget_FrameLayout parentClass)
    {
        super(classMgr,"android.widget.CalendarView",parentClass);
    }

    @Override
    public PendingViewPostCreateProcess createPendingViewPostCreateProcess(final View view, ViewGroup viewParent)
    {
        PendingViewPostCreateProcess pendingViewPostCreateProcess = super.createPendingViewPostCreateProcess(view, viewParent);

        pendingViewPostCreateProcess.addPendingSetAttribsTask(new Runnable()
        {
            @Override
            public void run()
            {
                // Esto es raro de narices pero es un workaround de un buggy de CalendarView al crearse programáticamente
                // o bien es por nuestra "culpa" al aplicar los atributos en un orden incorrecto, el caso es que la semana
                // actual no se selecciona, llamando a calendarView.setDate(System.currentTimeMillis()) no reacciona
                // pero sí lo hace si cambiamos mucho la fecha
                CalendarView calView = (CalendarView) view;
                long current = System.currentTimeMillis();
                calView.setDate(current - 7 * 24 * 60 * 60 * 1000);
                calView.setDate(current);
                //calView.setDate(current,true,true);
            }
        });

        return pendingViewPostCreateProcess;
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescView_widget_CalendarView_dateTextAppearance(this));
        addAttrDescAN(new AttrDescReflecMethodInt(this, "firstDayOfWeek", Calendar.getInstance(Locale.getDefault()).getFirstDayOfWeek())); // El valor por defecto no es exactamente así pero es razonable

        // Desde level 16 existe el método setFocusedMonthDateColor. En lollipop (level 21) ya no hay mFocusedMonthDateColor
        addAttrDescAN(new AttrDescReflecMethodColor(this, "focusedMonthDateColor", "setFocusedMonthDateColor", 0));

        addAttrDescAN(new AttrDescView_widget_CalendarView_maxDate_minDate(this, "maxDate"));
        addAttrDescAN(new AttrDescView_widget_CalendarView_maxDate_minDate(this, "minDate"));

        // Ver notas de focusedMonthDateColor
        addAttrDescAN(new AttrDescReflecMethodDrawable(this, "selectedDateVerticalBar", "setSelectedDateVerticalBar", "@null"));

        // Ver notas de focusedMonthDateColor
        addAttrDescAN(new AttrDescReflecMethodColor(this, "selectedWeekBackgroundColor", "setSelectedWeekBackgroundColor", 0));

        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "showWeekNumber", true));

        // Ver notas de focusedMonthDateColor
        addAttrDescAN(new AttrDescReflecMethodInt(this, "shownWeekCount", "setShownWeekCount", 6));

        // Ver notas de focusedMonthDateColor
        addAttrDescAN(new AttrDescReflecMethodColor(this, "unfocusedMonthDateColor", "setUnfocusedMonthDateColor", 0));

        addAttrDescAN(new AttrDescView_widget_CalendarView_weekDayTextAppearance(this));

        // Ver notas de focusedMonthDateColor
        addAttrDescAN(new AttrDescReflecMethodColor(this, "weekNumberColor", "setWeekNumberColor", 0));

        // Ver notas de focusedMonthDateColor
        addAttrDescAN(new AttrDescReflecMethodColor(this, "weekSeparatorLineColor", "setWeekSeparatorLineColor", 0));
    }
}

