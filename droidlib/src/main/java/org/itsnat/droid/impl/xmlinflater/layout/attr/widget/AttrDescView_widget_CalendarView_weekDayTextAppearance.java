package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.os.Build;
import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_CalendarView_weekDayTextAppearance extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    protected MethodContainer<Boolean> method;

    public AttrDescView_widget_CalendarView_weekDayTextAppearance(ClassDescViewBased parent)
    {
        super(parent,"weekDayTextAppearance");

        if (Build.VERSION.SDK_INT <= MiscUtil.ICE_CREAM_SANDWICH_MR1) // 4.0.3 Level 15
            this.method = new MethodContainer<Boolean>(parent.getDeclaredClass(),"setUpHeader",new Class[]{int.class});
        else
            this.method = new MethodContainer<Boolean>(parent.getDeclaredClass(),"setWeekDayTextAppearance",new Class[]{int.class});
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        int id = getIdentifier(attr.getValue(),attrCtx.getContext());

        method.invoke(view, id);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        // Android tiene un estilo por defecto
    }

}
