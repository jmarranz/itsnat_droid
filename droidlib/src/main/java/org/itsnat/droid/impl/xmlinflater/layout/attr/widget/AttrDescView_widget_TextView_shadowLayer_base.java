package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_shadowLayer_base extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    protected FieldContainer<Integer> fieldShadowColor;
    protected MethodContainer<Integer> methodShadowColor;
    protected FieldContainer<Float> fieldShadowRadius;
    protected FieldContainer<Float> fieldShadowDx;
    protected FieldContainer<Float> fieldShadowDy;


    public AttrDescView_widget_TextView_shadowLayer_base(ClassDescViewBased parent, String name)
    {
        super(parent,name);

        if (Build.VERSION.SDK_INT <= MiscUtil.ICE_CREAM_SANDWICH_MR1) // 4.0.3 Level 15
            this.fieldShadowColor = new FieldContainer<Integer>(Paint.class,"shadowColor");
        else // Partir de la versión siguiente (level 16) hay un método getShadowColor(), en teoría se podría seguir usando el atributo interno shadowColor de Paint pero en Level 21 (Lollipop) desaparece, usar el método desde level 16 es la mejor opción
            this.methodShadowColor = new MethodContainer<Integer>(parent.getDeclaredClass(),"getShadowColor");

        this.fieldShadowRadius = new FieldContainer<Float>(parent.getDeclaredClass(),"mShadowRadius");
        this.fieldShadowDx = new FieldContainer<Float>(parent.getDeclaredClass(),"mShadowDx");
        this.fieldShadowDy = new FieldContainer<Float>(parent.getDeclaredClass(),"mShadowDy");
    }

    private int getShadowColor(TextView textView)
    {
        return fieldShadowColor != null ? fieldShadowColor.get(textView.getPaint()) : methodShadowColor.invoke(textView);
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        TextView textView = (TextView)view;

        float radius = -1;
        float dx = -1;
        float dy = -1;
        int color = -1;

        Context ctx = attrCtx.getContext();
        String value = attr.getValue();
        if (name.equals("shadowColor"))
        {
            int convValue = getColor(attr, ctx,attrCtx.getXMLInflater());

            radius = fieldShadowRadius.get(textView);
            dx = fieldShadowDx.get(textView);
            dy = fieldShadowDy.get(textView);
            color = convValue;
        }
        else if (name.equals("shadowDx"))
        {
            float convValue = getFloat(value,ctx);

            radius = fieldShadowRadius.get(textView);
            dx = convValue;
            dy = fieldShadowDy.get(textView);
            color = getShadowColor(textView);
        }
        else if (name.equals("shadowDy"))
        {
            float convValue = getFloat(value, ctx);

            radius = fieldShadowRadius.get(textView);
            dx = fieldShadowDx.get(textView);
            dy = convValue;
            color = getShadowColor(textView);
        }
        else if (name.equals("shadowRadius"))
        {
            float convValue = getFloat(value, ctx);

            radius = convValue;
            dx = fieldShadowDx.get(textView);
            dy = fieldShadowDy.get(textView);
            color = getShadowColor(textView);
        }

        textView.setShadowLayer(radius,dx,dy,color);
    }

    @Override
    public void removeAttribute(View view, AttrLayoutContext attrCtx)
    {
        String defaultValue = null;
        if (name.equals("shadowColor"))   defaultValue = "0";
        else if (name.equals("shadowDx")) defaultValue = "0";
        else if (name.equals("shadowDy")) defaultValue = "0";
        else if (name.equals("shadowRadius")) defaultValue = "0";

        setToRemoveAttribute(view, defaultValue,attrCtx);
    }

}
