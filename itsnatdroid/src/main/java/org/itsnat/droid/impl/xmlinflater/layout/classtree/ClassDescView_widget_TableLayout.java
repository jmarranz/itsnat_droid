package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.view.ContextThemeWrapper;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TableLayout_collapseColumns;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TableLayout_shrinkColumns;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_TableLayout_stretchColumns;

import java.util.List;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_TableLayout extends ClassDescViewBased
{
    private static final int[] layoutParamsAttrs = new int[] {android.R.attr.layout_column,android.R.attr.layout_span};
    private static final String[] layoutParamsNames = new String[] {"layout_column","layout_span"};

    public ClassDescView_widget_TableLayout(ClassDescViewMgr classMgr, ClassDescView_widget_LinearLayout parentClass)
    {
        super(classMgr,"android.widget.TableLayout",parentClass);
    }

    public static void getTableRowLayoutParamsFromStyleId(int styleId,List<DOMAttr> styleLayoutParamsAttribs,ContextThemeWrapper ctx)
    {
        if (styleId == 0) throw MiscUtil.internalError();

        Configuration configuration = ctx.getResources().getConfiguration();

        TypedArray a = ctx.obtainStyledAttributes(styleId, layoutParamsAttrs);
        for(int i = 0; i < layoutParamsAttrs.length; i++)
        {
            if (!a.hasValue(i))
                continue;

            // Nada especial, layout_column y layout_span son números enteros (no dimensiones)
            String value = a.getString(i);

            String name = layoutParamsNames[i];

            DOMAttr attr = DOMAttr.createDOMAttr(NamespaceUtil.XMLNS_ANDROID, name, value);
            styleLayoutParamsAttribs.add(attr);
        }

        a.recycle();

        // Llamamos a la clase base:
        ClassDescView_widget_LinearLayout.getLinearLayoutLayoutParamsFromStyleId(styleId, styleLayoutParamsAttribs, ctx);
    }

    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescView_widget_TableLayout_collapseColumns(this));
        addAttrDescAN(new AttrDescView_widget_TableLayout_shrinkColumns(this));
        addAttrDescAN(new AttrDescView_widget_TableLayout_stretchColumns(this));

    }
}

