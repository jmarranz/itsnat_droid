package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import android.content.res.TypedArray;
import android.os.Build;
import android.view.ContextThemeWrapper;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.shared.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameMultiple;

import java.util.List;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_FrameLayout extends ClassDescViewBased
{
    private static final int[] layoutParamsAttrs = new int[]{android.R.attr.layout_gravity};
    private static final String[] layoutParamsNames = new String[] {"layout_gravity"};

    public ClassDescView_widget_FrameLayout(ClassDescViewMgr classMgr,ClassDescView_view_ViewGroup parentClass)
    {
        super(classMgr,"android.widget.FrameLayout",parentClass);
    }

    public static void getFrameLayoutLayoutParamsFromStyleId(int styleId,List<DOMAttr> styleLayoutParamsAttribs,ContextThemeWrapper ctx)
    {
        if (styleId == 0) throw new ItsNatDroidException("Internal Error");

        TypedArray a = ctx.obtainStyledAttributes(styleId, layoutParamsAttrs);
        for(int i = 0; i < layoutParamsAttrs.length; i++)
        {
            if (!a.hasValue(i))
                continue;

            int value = a.getInt(i,0);

            String name = layoutParamsNames[i];
            // Esperamos sólo name = "layout_gravity"
            String valueStr = GravityUtil.getNameFromValue(value); // Ej 0x30 | 0x50 => "top|bottom"

            DOMAttr attr = DOMAttr.create(NamespaceUtil.XMLNS_ANDROID,name,valueStr);
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

        if (Build.VERSION.SDK_INT < MiscUtil.MARSHMALLOW) // < 23
        {
            addAttrDescAN(new AttrDescReflecMethodDrawable(this, "foreground", "@null")); // A partir de MARSHMALLOW se define en View
        }
        addAttrDescAN(new AttrDescReflecMethodNameMultiple(this, "foregroundGravity", GravityUtil.nameValueMap, "fill"));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "measureAllChildren", false));

    }
}

