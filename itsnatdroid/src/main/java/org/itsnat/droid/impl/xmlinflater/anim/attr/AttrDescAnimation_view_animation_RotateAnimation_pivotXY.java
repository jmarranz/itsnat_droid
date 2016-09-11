package org.itsnat.droid.impl.xmlinflater.anim.attr;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.droid.impl.xmlinflater.PercFloat;
import org.itsnat.droid.impl.xmlinflater.anim.AttrAnimationContext;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimationRotate;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescAnimation_view_animation_RotateAnimation_pivotXY extends AttrDesc<ClassDescAnimationRotate,RotateAnimation,AttrAnimationContext>
{
    protected FieldContainer<Integer> fieldMPivotXYType;
    protected FieldContainer<Float> fieldMPivotXYValue;
    protected MethodContainer<Void> methodInitializePivotPoint;

    public AttrDescAnimation_view_animation_RotateAnimation_pivotXY(ClassDescAnimationRotate parent, String name)
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

        this.fieldMPivotXYType = new FieldContainer<Integer>(RotateAnimation.class, fieldPivotXYTypeName);
        this.fieldMPivotXYValue = new FieldContainer<Float>(RotateAnimation.class, fieldPivotXYValueName);

        this.methodInitializePivotPoint = new MethodContainer<Void>(RotateAnimation.class,"initializePivotPoint");
    }

    @Override
    public void setAttribute(RotateAnimation rotateAnimation, DOMAttr attr, AttrAnimationContext attrCtx)
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

        fieldMPivotXYType.set(rotateAnimation,pivotType);
        fieldMPivotXYValue.set(rotateAnimation,pivotValue);
        methodInitializePivotPoint.invoke(rotateAnimation); // rotateAnimation.initializePivotPoint()
    }

    @Override
    public void removeAttribute(RotateAnimation rotateAnimation, AttrAnimationContext attrCtx)
    {
        setAttributeToRemove(rotateAnimation, "0", attrCtx);
    }
}
