package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_ViewGroup_animateLayoutChanges;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_ViewGroup_descendantFocusability;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_ViewGroup_layoutAnimation;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_ViewGroup_persistentDrawingCache;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_margin;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_marginBottom;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_marginLeft;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_marginRight;
import org.itsnat.droid.impl.xmlinflater.layout.attr.view.AttrDescView_view_View_layout_marginTop;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodBoolean;

import java.util.List;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_view_ViewGroup extends ClassDescViewBased
{
    public ClassDescView_view_ViewGroup(ClassDescViewMgr classMgr, ClassDescView_view_View parentClass)
    {
        super(classMgr,"android.view.ViewGroup",parentClass);
    }

    public static void getViewGroupLayoutParamsFromStyleId(int styleId, ViewGroup.LayoutParams layoutParams, List<DOMAttr> styleLayoutParamsAttribs, Context ctx)
    {
        if (styleId == 0) throw new ItsNatDroidException("Unexpected");

        int[] attrs = new int[]{android.R.attr.layout_width,android.R.attr.layout_height}; // android.R.attr.layout_width etc pre-existing en Android, de otra manera habría que declararlos como <declare-styleable>
        String[] names = new String[] {"layout_width","layout_height"};
        TypedArray a = ctx.obtainStyledAttributes(styleId, attrs);
        // Esperamos 2 resultados aunque no existan, si se utilizara a.getValue(index,outValue) el type del TypedValue outValue sería TypedValue.TYPE_NULL
        for(int i = 0; i < attrs.length; i++)
        {
            String value = a.getString(i);
            if (value == null)
                continue;

            String name = names[i];
            if (value.equals("" + ViewGroup.LayoutParams.MATCH_PARENT))
                value = "match_parent";
            else if (value.equals("" + ViewGroup.LayoutParams.WRAP_CONTENT))
                value = "wrap_content";
            // Si no es uno de los casos anteriores esperamos el valor original ej "20dp"

            DOMAttr attr = DOMAttr.create(InflatedXML.XMLNS_ANDROID,name,value);
            styleLayoutParamsAttribs.add(attr);
        }

        a.recycle();
    }

    public static void getViewGroupMarginLayoutParamsFromStyleId(int styleId,ViewGroup.MarginLayoutParams layoutParams,List<DOMAttr> styleLayoutParamsAttribs,Context ctx)
    {
        if (styleId == 0) throw new ItsNatDroidException("Unexpected");

        int[] attrs = new int[]{android.R.attr.layout_marginBottom,android.R.attr.layout_marginLeft,android.R.attr.layout_marginTop,android.R.attr.layout_marginRight,android.R.attr.layout_margin};
        String[] names = new String[] {"layout_marginBottom","layout_marginLeft","layout_marginTop","layout_marginRight","layout_margin"};
        TypedArray a = ctx.obtainStyledAttributes(styleId, attrs);
        for(int i = 0; i < attrs.length; i++)
        {
            String value = a.getString(i);
            if (value == null)
                continue;

            String name = names[i];

            DOMAttr attr = DOMAttr.create(InflatedXML.XMLNS_ANDROID,name,value);
            styleLayoutParamsAttribs.add(attr);
        }

        a.recycle();
    }


    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "addStatesFromChildren", false));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "alwaysDrawnWithCache", "setAlwaysDrawnWithCacheEnabled", true));
        addAttrDescAN(new AttrDescView_view_ViewGroup_animateLayoutChanges(this)); // animateLayoutChanges
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "animationCache", "setAnimationCacheEnabled", true));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "clipChildren", true));
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "clipToPadding", true));
        addAttrDescAN(new AttrDescView_view_ViewGroup_descendantFocusability(this)); // descendantFocusability
        addAttrDescAN(new AttrDescView_view_ViewGroup_layoutAnimation(this)); // layoutAnimation
        // android:layoutMode es Level 18
        addAttrDescAN(new AttrDescView_view_ViewGroup_persistentDrawingCache(this)); // persistentDrawingCache
        addAttrDescAN(new AttrDescReflecMethodBoolean(this, "splitMotionEvents", "setMotionEventSplittingEnabled", false));

    }
}

