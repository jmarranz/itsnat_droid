package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.shared.GravityUtil;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodId;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodNameMultiple;

import java.util.List;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_RelativeLayout extends ClassDescViewBased
{
    // No incluimos los nuevos params de la API 17
    private static final int[] layoutParamsAttrs = new int[]{android.R.attr.layout_above,android.R.attr.layout_alignBaseline,android.R.attr.layout_alignBottom,android.R.attr.layout_alignLeft,
            android.R.attr.layout_alignParentBottom,android.R.attr.layout_alignParentLeft,android.R.attr.layout_alignParentRight,android.R.attr.layout_alignParentTop,
            android.R.attr.layout_alignRight,android.R.attr.layout_alignTop,android.R.attr.layout_alignWithParentIfMissing,android.R.attr.layout_below,
            android.R.attr.layout_centerHorizontal,android.R.attr.layout_centerInParent,android.R.attr.layout_centerVertical,android.R.attr.layout_toLeftOf,android.R.attr.layout_toRightOf};

    private static final String[] layoutParamsNames = new String[] {"layout_above","layout_alignBaseline","layout_alignBottom","layout_alignLeft",
            "layout_alignParentBottom","layout_alignParentLeft","layout_alignParentRight","layout_alignParentTop",
            "layout_alignRight","layout_alignTop","layout_alignWithParentIfMissing","layout_below",
            "layout_centerHorizontal","layout_centerInParent","layout_centerVertical","layout_toLeftOf","layout_toRightOf"};

    public ClassDescView_widget_RelativeLayout(ClassDescViewMgr classMgr,ClassDescView_view_ViewGroup parentClass)
    {
        super(classMgr,"android.widget.RelativeLayout",parentClass);
    }

    public static void getRelativeLayoutLayoutParamsFromStyleId(int styleId,List<DOMAttr> styleLayoutParamsAttribs,ContextThemeWrapper ctx)
    {
        if (styleId == 0) throw MiscUtil.internalError();

        TypedArray a = ctx.obtainStyledAttributes(styleId, layoutParamsAttrs);
        for(int i = 0; i < layoutParamsAttrs.length; i++)
        {
            if (!a.hasValue(i))
                continue;

            // Los atributos son de dos tipos, o boolean o de tipo "id"
            String value;
            TypedValue outValue = a.peekValue(i);
            if (outValue.type == TypedValue.TYPE_INT_BOOLEAN)
            {
                value = a.getString(i); // Devolverá "true" o "false" que es lo que queremos
            }
            else if (outValue.type == TypedValue.TYPE_STRING)
            {
                value = a.getString(i); // Devolverá "textViewTest1" si originalmente es "@id/textViewTest1" o también devolverá lo mismo aunque el original sea un "@id/test_layout_below_id" que referencie a un <item name="test_layout_below_id" type="id">@id/textViewTest1</item>, al final getString(int) devuelve el nombre último referenciado
                value = "@+id/" + value; // Añadimos el "+" por prudencia
            }
            else throw MiscUtil.internalError();

            String name = layoutParamsNames[i];

            DOMAttr attr = DOMAttr.create(NamespaceUtil.XMLNS_ANDROID,name,value);
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

        addAttrDescAN(new AttrDescReflecMethodNameMultiple(this, "gravity", GravityUtil.nameValueMap, "left|top"));
        addAttrDescAN(new AttrDescReflecMethodId(this, "ignoreGravity", -1));
    }
}

