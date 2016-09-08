package org.itsnat.droid.impl.xmlinflater.anim.attr;

import android.os.Build;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.PercFloat;
import org.itsnat.droid.impl.xmlinflater.anim.AttrAnimationContext;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimationScale;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescAnimation_view_animation_ScaleAnimation_pivotXY extends AttrDesc<ClassDescAnimationScale,ScaleAnimation,AttrAnimationContext>
{
    protected FieldContainer<Integer> fieldMPivotXYType;
    protected FieldContainer<Float> fieldMPivotXYValue;
    protected MethodContainer<Void> methodInitializePivotPoint;

    public AttrDescAnimation_view_animation_ScaleAnimation_pivotXY(ClassDescAnimationScale parent, String name)
    {
        super(parent,name);

        if (!"pivotX".equals(name) && !"pivotY".equals(name)) throw MiscUtil.internalError();

        String fieldPivotXYTypeName;
        String fieldPivotXYValueName;
        if ("pivotX".equals(name))
        {
            fieldPivotXYTypeName = "mPivotXType";
            fieldPivotXYValueName = "mPivotXValue";
        }
        else
        {
            fieldPivotXYTypeName = "mPivotYType";
            fieldPivotXYValueName = "mPivotYValue";
        }

        this.fieldMPivotXYType = new FieldContainer<Integer>(ScaleAnimation.class, fieldPivotXYTypeName);
        this.fieldMPivotXYValue = new FieldContainer<Float>(ScaleAnimation.class, fieldPivotXYValueName);

        this.methodInitializePivotPoint = new MethodContainer<Void>(ScaleAnimation.class,"initializePivotPoint");
    }

    @Override
    public void setAttribute(ScaleAnimation scaleAnimation, DOMAttr attr, AttrAnimationContext attrCtx)
    {
        PercFloat convValue = getPercFloat(attr.getResourceDesc(),attrCtx.getXMLInflaterContext());

        int pivotType;
        float pivotValue;
        if (convValue.isFraction())
        {
            pivotType = convValue.isFractionParent() ? Animation.RELATIVE_TO_PARENT : Animation.RELATIVE_TO_SELF;
            pivotValue = convValue.toFloatBasedOnDataType();
        }
        else
        {
            pivotType = Animation.ABSOLUTE;
            pivotValue = convValue.toFloatBasedOnDataType();
        }

        fieldMPivotXYType.set(scaleAnimation,pivotType);
        fieldMPivotXYValue.set(scaleAnimation,pivotValue);
        methodInitializePivotPoint.invoke(scaleAnimation); // scaleAnimation.initializePivotPoint()
    }

    @Override
    public void removeAttribute(ScaleAnimation scaleAnimation, AttrAnimationContext attrCtx)
    {
        setAttributeToRemove(scaleAnimation, "0", attrCtx);
    }
}
