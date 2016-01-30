package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethod;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_ImageView_tint extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    protected String defaultValue;
    protected MethodContainer methodSetTint;
    protected MethodContainer methodSetImageTintMode; // LOLLIPOP and sup

    public AttrDescView_widget_ImageView_tint(ClassDescViewBased parent)
    {
        super(parent,"tint");
        this.defaultValue = getDefaultValue();

        // A partir de Lollipop (level 21) ya no se usa setColorFilter, se usa setImageTintList, en teoría el resultado
        // visual debería ser el mismo pero a nivel de estado interno no.
        // Si diera problemas setImageTintList podemos poner USE_EVER_OLD_APPROACH = true pues el caso es que setColorFilter SIGUE EXISTIENDO y no está deprecated por lo que podemos NO usar setImageTintList
        // pero como hemos conseguido que funcione igual pues usamos la versión moderna cuando proceda que es la que tiene futuro

        if (Build.VERSION.SDK_INT < MiscUtil.LOLLIPOP)
        {
            this.methodSetTint = new MethodContainer(parent.getDeclaredClass(),"setColorFilter",int.class);
        }
        else
        {
            this.methodSetTint = new MethodContainer(parent.getDeclaredClass(),"setImageTintList",ColorStateList.class);
            this.methodSetImageTintMode = new MethodContainer(parent.getDeclaredClass(), "setImageTintMode", new Class[]{PorterDuff.Mode.class});
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

        if (Build.VERSION.SDK_INT < MiscUtil.LOLLIPOP)
        {
            methodSetTint.invoke(view,convValue);
        }
        else
        {
            methodSetImageTintMode.invoke(view, PorterDuff.Mode.SRC_ATOP); // SRC_ATOP es el método por defecto, es necesario llamarlo
            methodSetTint.invoke(view, ColorStateList.valueOf(convValue));
        }
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        if (defaultValue != null)
            setToRemoveAttribute(view, defaultValue,attrCtx);
    }
}
