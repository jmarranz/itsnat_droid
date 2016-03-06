package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.ViewStyleAttr;
import org.itsnat.droid.impl.xmlinflater.layout.ViewStyleAttrDynamic;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_CalendarView_dateTextAppearance extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    protected FieldContainer<int[]> fieldTextAppearance;
    protected FieldContainer<Integer> fieldTextAppearance_textSize;
    protected FieldContainer<Integer> fieldTextAppearance_Small;
    protected FieldContainer<Integer> fieldDateTextSize;
    protected MethodContainer<Void> methodDateTextAppearance;

    public AttrDescView_widget_CalendarView_dateTextAppearance(ClassDescViewBased parent)
    {
        super(parent,"dateTextAppearance");

        Class class_R_styleable = getClass_R_styleable(); // com.android.internal.R$styleable
        this.fieldTextAppearance_Small = new FieldContainer<Integer>(class_R_styleable, "TextAppearance_Small");

        if (Build.VERSION.SDK_INT <= MiscUtil.ICE_CREAM_SANDWICH_MR1)
        {
            this.fieldTextAppearance = new FieldContainer<int[]>(class_R_styleable, "TextAppearance"); // com.android.internal.R.styleable.TextAppearance
            this.fieldTextAppearance_textSize = new FieldContainer<Integer>(class_R_styleable, "TextAppearance_textSize"); // com.android.internal.R.styleable.TextAppearance_textSize
            this.fieldDateTextSize = new FieldContainer<Integer>(parent.getDeclaredClass(), "mDateTextSize");
        }
        else // A partir de level 16 hay un método setDateTextAppearance (int resourceId). Podríamos usar lo de ICS pero en Lollipop cambia CalendarView a fondo usando un "delegate"
        {
            this.methodDateTextAppearance = new MethodContainer<Void>(parent.getDeclaredClass(),"setDateTextAppearance",new Class[]{int.class});
        }
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        Context ctx = attrCtx.getContext();
        ViewStyleAttr style = getViewStyle(attr, attrCtx.getXMLInflaterLayout());
        List<DOMAttr> styleItemsDynamicAttribs = (style instanceof ViewStyleAttrDynamic) ? new ArrayList<DOMAttr>() : null;
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

        if (Build.VERSION.SDK_INT <= MiscUtil.ICE_CREAM_SANDWICH_MR1)
        {
            int[] textAppearanceStyleArr = fieldTextAppearance.get(null);

            int textSizeStyle = fieldTextAppearance_textSize.get(null);

            int dateTextSize;
            TypedArray dateTextAppearance = attrCtx.getContext().obtainStyledAttributes(dateTextAppearanceResId, textAppearanceStyleArr);
            try
            {
                dateTextSize = dateTextAppearance.getDimensionPixelSize(textSizeStyle, 14); // DEFAULT_DATE_TEXT_SIZE = 14
            }
            finally
            {
                dateTextAppearance.recycle();
            }

            fieldDateTextSize.set(view, dateTextSize);
        }
        else
        {
            methodDateTextAppearance.invoke(view,dateTextAppearanceResId);
        }
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        // Se usa el valor por defecto de Android
        setToRemoveAttribute(view, "0", attrCtx);

    }
}
