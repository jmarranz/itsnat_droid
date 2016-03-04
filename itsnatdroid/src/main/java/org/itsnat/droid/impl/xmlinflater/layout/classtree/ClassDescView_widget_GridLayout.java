package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.view.ContextThemeWrapper;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.OrientationUtil;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_GridLayout_alignmentMode;
import org.itsnat.droid.impl.xmlinflater.shared.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodInt;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameSingle;

import java.util.List;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_GridLayout extends ClassDescViewBased
{
    private static final int[] layoutParamsAttrs = new int[]{android.R.attr.layout_gravity,android.R.attr.layout_column,android.R.attr.layout_columnSpan,
                                                            android.R.attr.layout_row,android.R.attr.layout_rowSpan};
    private static final String[] layoutParamsNames = new String[] {"layout_gravity","layout_column","layout_columnSpan",
                                                            "layout_row","layout_rowSpan"};

    public ClassDescView_widget_GridLayout(ClassDescViewMgr classMgr,ClassDescView_view_ViewGroup parentClass)
    {
        super(classMgr,"android.widget.GridLayout",parentClass);
    }

    public static void getGridLayoutLayoutParamsFromStyleId(int styleId,List<DOMAttr> styleLayoutParamsAttribs,ContextThemeWrapper ctx)
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
                // Los demás atributos son enteros normales
                value = a.getString(i);
            }

            DOMAttr attr = DOMAttr.create(NamespaceUtil.XMLNS_ANDROID,name,value);
            styleLayoutParamsAttribs.add(attr);
        }

        a.recycle();

        // Llamamos a la clase base
        ClassDescView_view_ViewGroup.getViewGroupMarginLayoutParamsFromStyleId(styleId, styleLayoutParamsAttribs, ctx);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescView_widget_GridLayout_alignmentMode(this));
        addAttrDescAN(new AttrDescReflecMethodInt(this, "columnCount", Integer.MIN_VALUE)); // El MIN_VALUE está sacado del código fuente
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "columnOrderPreserved", true));
        addAttrDescAN(new AttrDescReflecMethodNameSingle(this, "orientation", int.class, OrientationUtil.nameValueMap, "horizontal"));

        addAttrDescAN(new AttrDescReflecMethodInt(this, "rowCount", Integer.MIN_VALUE)); // El MIN_VALUE está sacado del código fuente
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "rowOrderPreserved", true));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "useDefaultMargins", false));

    }
}

