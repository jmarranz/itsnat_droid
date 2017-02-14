package org.itsnat.droid.impl.xmlinflater.anim.attr;

import android.view.animation.ScaleAnimation;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.PercFloatImpl;
import org.itsnat.droid.impl.xmlinflater.anim.AttrAnimationContext;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimationScale;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescAnimation_view_animation_ScaleAnimation_fromToXYScale extends AttrDesc<ClassDescAnimationScale,ScaleAnimation,AttrAnimationContext>
{
    protected FieldContainer<Float> fieldMFromToXY;

    public AttrDescAnimation_view_animation_ScaleAnimation_fromToXYScale(ClassDescAnimationScale parent, String name)
    {
        super(parent,name);


        String fieldMFromToXYName;
        if ("fromXScale".equals(name))
        {
            fieldMFromToXYName = "mFromX";
        }
        else if ("fromYScale".equals(name))
        {
            fieldMFromToXYName = "mFromY";
        }
        else if ("toXScale".equals(name))
        {
            fieldMFromToXYName = "mToX";
        }
        else if ("toYScale".equals(name))
        {
            fieldMFromToXYName = "mToY";
        }
        else throw MiscUtil.internalError();

        this.fieldMFromToXY = new FieldContainer<Float>(ScaleAnimation.class, fieldMFromToXYName);
    }

    @Override
    public void setAttribute(ScaleAnimation scaleAnimation, DOMAttr attr, AttrAnimationContext attrCtx)
    {
        PercFloatImpl convValue = getDimensionPercFloat(attr.getResourceDesc(),attrCtx.getXMLInflaterContext());

        // En PercFloat.toFloatBasedOnDataType() acabamos calculando tanto si es % (10% => 0.1) como si es float normal (ej 1.5) o es un dimension (se calcula el resultado en pixeles)
        // por tanto no necesitamos mFromXType y mFromXData (idem Y y los To), sólo mFromX . Es como si hubieramos especificado siempre un float calculado ya el valor final

        fieldMFromToXY.set(scaleAnimation,convValue.toFloatBasedOnDataType());
    }

    @Override
    public void removeAttribute(ScaleAnimation scaleAnimation, AttrAnimationContext attrCtx)
    {
        setAttributeToRemove(scaleAnimation, "0", attrCtx);
    }
}
