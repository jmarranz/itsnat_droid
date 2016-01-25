package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.content.res.ColorStateList;
import android.os.Build;
import android.view.View;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethod;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_ImageView_tint extends AttrDescReflecMethod<ClassDescViewBased,View,AttrLayoutContext>
{
    public static boolean USE_EVER_OLD_APPROACH = true;

    protected String defaultValue;

    public AttrDescView_widget_ImageView_tint(ClassDescViewBased parent)
    {
        super(parent,"tint",getMethodName(),getClassParam());
        this.defaultValue = getDefaultValue();
    }

    protected static String getMethodName()
    {
        // A partir de Lollipop (level 21) ya no se usa setColorFilter, se usa setImageTintList, en teoría el resultado
        // visual debería ser el mismo pero a nivel de estado interno no, lo cual nos entorpece el testing
        // por otra parte NO he conseguido que setImageTintList visualmente se manifieste de forma igual al modo compilado aunque el test NO da error.
        // El caso es que setColorFilter SIGUE EXISTIENDO y no está deprecated por lo que NO usamos setImageTintList (es decir USE_EVER_OLD_APPROACH = true)

        if (USE_EVER_OLD_APPROACH)
        {
            return "setColorFilter";
        }
        else
        {
            return (Build.VERSION.SDK_INT < MiscUtil.LOLLIPOP) ? "setColorFilter" : "setImageTintList";
        }
    }

    protected static Class<?> getClassParam()
    {
        if (USE_EVER_OLD_APPROACH)
        {
            return int.class;
        }
        else
        {
            return (Build.VERSION.SDK_INT < MiscUtil.LOLLIPOP) ? int.class : ColorStateList.class;
        }
    }

    protected static String getDefaultValue()
    {
        return "#000000";
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        final int convValue = getColor(attr,attrCtx.getXMLInflater());

        if (USE_EVER_OLD_APPROACH)
        {
            callMethod(view, convValue);
        }
        else
        {
            if (Build.VERSION.SDK_INT < MiscUtil.LOLLIPOP)
            {
                callMethod(view, convValue);
            }
            else
            {
                callMethod(view, new ColorStateList(new int[1][0], new int[]{convValue}));
            }
        }
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        if (defaultValue != null)
            setToRemoveAttribute(view, defaultValue,attrCtx);
    }
}
