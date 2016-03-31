package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.PendingViewPostCreateProcess;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_DatePicker_maxDate_minDate extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    private static final int DEFAULT_START_YEAR = 1900;
    private static final int DEFAULT_END_YEAR = 2100;

    protected FieldContainer<Object> fieldDelegate;
    protected FieldContainer<Locale> fieldCurrentLocale;
    protected MethodContainer<Boolean> methodParseDate;
    protected MethodContainer<Void> methodMaxMinDate;

    public AttrDescView_widget_DatePicker_maxDate_minDate(ClassDescViewBased parent, String name)
    {
        super(parent,name);

        Class datePickerClass1,datePickerClass2;
        if (Build.VERSION.SDK_INT < MiscUtil.LOLLIPOP)
        {
            datePickerClass1 = parent.getDeclaredClass();
            datePickerClass2 = datePickerClass1;
        }
        else // Lollipop
        {
            this.fieldDelegate = new FieldContainer<Object>(parent.getDeclaredClass(), "mDelegate");
            datePickerClass1 = MiscUtil.resolveClass(DatePicker.class.getName() + "$AbstractDatePickerDelegate");
            datePickerClass2 = MiscUtil.resolveClass(DatePicker.class.getName() + "$DatePickerSpinnerDelegate");
        }

        this.fieldCurrentLocale = new FieldContainer<Locale>(datePickerClass1, "mCurrentLocale");
        this.methodParseDate = new MethodContainer<Boolean>(datePickerClass2,"parseDate",new Class[]{String.class,Calendar.class});


        String methodName = null;
        if ("maxDate".equals(name))
            methodName = "setMaxDate";
        else if ("minDate".equals(name))
            methodName = "setMinDate";

        this.methodMaxMinDate = new MethodContainer<Void>(datePickerClass2,methodName,new Class[]{long.class});
    }

    @Override
    public void setAttribute(final View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        final String date = getString(attr.getResourceDesc(), attrCtx.getXMLInflaterLayout());

        final Object datePickerObject = getDatePickerObject((DatePicker) view);

        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                // Delegamos al final porque los atributos maxDate y minDate tienen prioridad (ganan si están definidos)
                // sobre startYear y endYear
                Locale currentLocale = fieldCurrentLocale.get(datePickerObject);
                Calendar tempDate = Calendar.getInstance(currentLocale);
                tempDate.clear();

                if (!TextUtils.isEmpty(date))
                {
                    if (!parseDate(datePickerObject,date, tempDate)) // El código fuente de Android tolera un mal formato, nosotros no pues no hace más que complicarlo t_odo
                        throw new ItsNatDroidException("Date: " + date + " not in format: " + "MM/dd/yyyy");
                }
                else
                {
                    // Caso de eliminación de atributo, intrepretamos como el deseo de poner los valores por defecto (más o menos es así en el código fuente)
                    // hay que tener en cuenta que es un valor explícito por lo que ignoramos/reemplazamos los posibles valores puestos
                    // por los atributos endYear/startYear explícitamente

                    if ("maxDate".equals(name))
                        tempDate.set(DEFAULT_END_YEAR, Calendar.DECEMBER /*11*/, 31);
                    else if ("minDate".equals(name))
                        tempDate.set(DEFAULT_START_YEAR,Calendar.JANUARY /*0*/, 1);
                }

                methodMaxMinDate.invoke(datePickerObject,tempDate.getTimeInMillis());
            }
        };

        PendingViewPostCreateProcess pendingViewPostCreateProcess = attrCtx.getPendingViewPostCreateProcess();
        if (pendingViewPostCreateProcess != null)
            pendingViewPostCreateProcess.addPendingLayoutParamsTask(task);
        else
            task.run();
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        setToRemoveAttribute(view, "", attrCtx);
    }

    private Object getDatePickerObject(DatePicker view)
    {
        if (Build.VERSION.SDK_INT < MiscUtil.LOLLIPOP)
            return view;
        else
            return fieldDelegate.get(view);
    }

    private boolean parseDate(Object datePickerObject,String date, Calendar outDate)
    {
        return methodParseDate.invoke(datePickerObject,date,outDate);
    }
}
