package org.itsnat.droid.impl.xmlinflater.layout.attr.widget;

import android.view.View;
import android.widget.TextView;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
import org.itsnat.droid.impl.xmlinflater.layout.AttrLayoutContext;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescView_widget_TextView_shadowLayer_base extends AttrDesc<ClassDescViewBased,View,AttrLayoutContext>
{
    protected MethodContainer<Integer> methodShadowColor;
    protected FieldContainer<Float> fieldShadowRadius;
    protected FieldContainer<Float> fieldShadowDx;
    protected FieldContainer<Float> fieldShadowDy;


    public AttrDescView_widget_TextView_shadowLayer_base(ClassDescViewBased parent, String name)
    {
        super(parent,name);

        // Partir de la versión 16 hay un método getShadowColor(), en teoría se podría seguir usando el atributo interno shadowColor de Paint pero en Level 21 (Lollipop) desaparece, usar el método desde level 16 es la mejor opción
        this.methodShadowColor = new MethodContainer<Integer>(parent.getDeclaredClass(),"getShadowColor");

        this.fieldShadowRadius = new FieldContainer<Float>(parent.getDeclaredClass(),"mShadowRadius");
        this.fieldShadowDx = new FieldContainer<Float>(parent.getDeclaredClass(),"mShadowDx");
        this.fieldShadowDy = new FieldContainer<Float>(parent.getDeclaredClass(),"mShadowDy");
    }

    private int getShadowColor(TextView textView)
    {
        return methodShadowColor.invoke(textView);
    }

    @Override
    public void setAttribute(View view, DOMAttr attr, AttrLayoutContext attrCtx)
    {
        TextView textView = (TextView)view;

        XMLInflaterContext xmlInflaterContext = attrCtx.getXMLInflaterContext();

        float radius = -1;
        float dx = -1;
        float dy = -1;
        int color = -1;

        //Context ctxToOpenInternFiles = attrCtx.getContext();
        //String value = attr.getValue();
        if (name.equals("shadowColor"))
        {
            int convValue = getColor(attr.getResourceDesc(),xmlInflaterContext);

            radius = fieldShadowRadius.get(textView);
            dx = fieldShadowDx.get(textView);
            dy = fieldShadowDy.get(textView);
            color = convValue;
        }
        else if (name.equals("shadowDx"))
        {
            float convValue = getFloat(attr.getResourceDesc(),xmlInflaterContext);

            radius = fieldShadowRadius.get(textView);
            dx = convValue;
            dy = fieldShadowDy.get(textView);
            color = getShadowColor(textView);
        }
        else if (name.equals("shadowDy"))
        {
            float convValue = getFloat(attr.getResourceDesc(),xmlInflaterContext);

            radius = fieldShadowRadius.get(textView);
            dx = fieldShadowDx.get(textView);
            dy = convValue;
            color = getShadowColor(textView);
        }
        else if (name.equals("shadowRadius"))
        {
            float convValue = getFloat(attr.getResourceDesc(),xmlInflaterContext);

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

        setAttributeToRemove(view, defaultValue,attrCtx);
    }

}
