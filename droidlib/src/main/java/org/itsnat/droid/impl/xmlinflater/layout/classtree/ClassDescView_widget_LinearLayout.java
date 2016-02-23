package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.view.ContextThemeWrapper;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.OrientationUtil;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_LinearLayout_baselineAlignedChildIndex;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_LinearLayout_showDividers;
import org.itsnat.droid.impl.xmlinflater.shared.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntRound;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodFloat;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameMultiple;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;

import java.util.List;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_LinearLayout extends ClassDescViewBased
{
    private static final int[] layoutParamsAttrs = new int[]{android.R.attr.layout_weight,android.R.attr.layout_gravity};
    private static final String[] layoutParamsNames = new String[] {"layout_weight","layout_gravity"};

    public ClassDescView_widget_LinearLayout(ClassDescViewMgr classMgr,ClassDescView_view_ViewGroup parentClass)
    {
        super(classMgr,"android.widget.LinearLayout",parentClass);
    }

    public static void getLinearLayoutLayoutParamsFromStyleId(int styleId, List<DOMAttr> styleLayoutParamsAttribs, ContextThemeWrapper ctx)
    {
        if (styleId == 0) throw MiscUtil.internalError();

        Configuration configuration = ctx.getResources().getConfiguration();

        TypedArray a = ctx.obtainStyledAttributes(styleId, layoutParamsAttrs);
        for(int i = 0; i < layoutParamsAttrs.length; i++)
        {
            if (!a.hasValue(i))
                continue;

            String name = layoutParamsNames[i];

            String value;
            if ("layout_gravity".equals(name))
            {
                int valueInt = a.getInt(i,0);
                value = GravityUtil.getNameFromValue(valueInt);
            }
            else
            {
                // layout_weight es un entero normal no dimensión
                value = a.getString(i);
            }

            DOMAttr attr = DOMAttr.create(NamespaceUtil.XMLNS_ANDROID,name, value,configuration);
            styleLayoutParamsAttribs.add(attr);
        }

        a.recycle();

        // Llamamos a la clase base
        ClassDescView_view_ViewGroup.getViewGroupMarginLayoutParamsFromStyleId(styleId,styleLayoutParamsAttribs, ctx);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "baselineAligned", true));
        addAttrDescAN(new AttrDescView_widget_LinearLayout_baselineAlignedChildIndex(this));
        addAttrDescAN(new AttrDescReflecMethodDrawable(this, "divider", "setDividerDrawable", null)); // Hay un drawable por defecto de Android
        // showDividers y dividerPadding atributos los he descubierto por casualidad en StackOverflow y resulta que son atributos NO documentados de LinearLayout (se ven en el código fuente)
        addAttrDescAN(new AttrDescView_widget_LinearLayout_showDividers(this));  // showDividers
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "dividerPadding", 0f));
        addAttrDescAN(new AttrDescReflecMethodNameMultiple(this, "gravity", GravityUtil.nameValueMap, "start|top"));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "measureWithLargestChild", "setMeasureWithLargestChildEnabled", false));
        addAttrDescAN(new AttrDescReflecMethodNameSingle(this, "orientation", int.class, OrientationUtil.nameValueMap, "horizontal"));
        addAttrDescAN(new AttrDescReflecMethodFloat(this, "weightSum", -1.0f));
    }
}

