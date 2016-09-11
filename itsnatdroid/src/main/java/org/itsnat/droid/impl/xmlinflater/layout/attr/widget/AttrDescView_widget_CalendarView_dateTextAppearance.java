package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.content.Context;
import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.ViewStyleAttribs;
import org.itsnat.droid.impl.xmlinflater.layout.ViewStyleAttribsDynamic;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_CalendarView_dateTextAppearance extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    protected FieldContainer<Integer> fieldTextAppearance_Small;
    protected MethodContainer<Void> methodDateTextAppearance;

    public AttrDescView_widget_CalendarView_dateTextAppearance(ClassDescViewBased parent)
    {
        super(parent,"dateTextAppearance");

        Class class_R_styleable = getClass_R_styleable(); // com.android.internal.R$styleable
        this.fieldTextAppearance_Small = new FieldContainer<Integer>(class_R_styleable, "TextAppearance_Small");

        // A partir de level 16 hay un método setDateTextAppearance (int resourceId). Podríamos usar lo de ICS pero en Lollipop cambia CalendarView a fondo usando un "delegate"
        this.methodDateTextAppearance = new MethodContainer<Void>(parent.getDeclaredClass(),"setDateTextAppearance",new Class[]{int.class});
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        Context ctx = attrCtx.getContext();
        ViewStyleAttribs style = getViewStyle(attr.getResourceDesc(), attrCtx.getXMLInflaterContext());
        List<DOMAttr> styleItemsDynamicAttribs = (style instanceof ViewStyleAttribsDynamic) ? new ArrayList<DOMAttr>() : null;
        int dateTextAppearanceResId = getViewStyle(style,styleItemsDynamicAttribs,ctx);

        setDateTextAppearanceResId(view,dateTextAppearanceResId,attrCtx);

        if (styleItemsDynamicAttribs != null)
        {
            ClassDescViewBased classDesc = getClassDesc();
            for(DOMAttr styleAttr : styleItemsDynamicAttribs)
            {
                classDesc.setAttributeOrInlineEventHandler(view,styleAttr,attrCtx);
            }
        }
    }

    private void setDateTextAppearanceResId(View view,int dateTextAppearanceResId,AttrLayoutContext attrCtx)
    {
        if (dateTextAppearanceResId <= 0) dateTextAppearanceResId = fieldTextAppearance_Small.get(null); // Valor por defecto

        methodDateTextAppearance.invoke(view,dateTextAppearanceResId);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        // Se usa el valor por defecto de Android
        setAttributeToRemove(view, "0", attrCtx);

    }
}
