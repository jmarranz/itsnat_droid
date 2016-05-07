package org.itsnat.droid.impl.xmlinflater.anim.attr;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.PercFloat;
import org.itsnat.droid.impl.xmlinflater.anim.AttrAnimationContext;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimationTranslate;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescAnimation_view_animation_TranslateAnimation_fromToXYDelta extends AttrDesc<ClassDescAnimationTranslate,TranslateAnimation,AttrAnimationContext>
{
    protected FieldContainer<Integer> fieldMFromToXYType;
    protected FieldContainer<Float> fieldMFromToXYValue;

    public AttrDescAnimation_view_animation_TranslateAnimation_fromToXYDelta(ClassDescAnimationTranslate parent, String name)
    {
        super(parent,name);


        String fieldMFromToXYTypeName;
        String fieldMFromToXYValueName;
        if ("fromXDelta".equals(name))
        {
            fieldMFromToXYTypeName = "mFromXType";
            fieldMFromToXYValueName = "mFromXValue";
        }
        else if ("fromYDelta".equals(name))
        {
            fieldMFromToXYTypeName = "mFromYType";
            fieldMFromToXYValueName = "mFromYValue";
        }
        else if ("toXDelta".equals(name))
        {
            fieldMFromToXYTypeName = "mToXType";
            fieldMFromToXYValueName = "mToXValue";
        }
        else if ("toYDelta".equals(name))
        {
            fieldMFromToXYTypeName = "mToYType";
            fieldMFromToXYValueName = "mToYValue";
        }
        else throw MiscUtil.internalError();

        this.fieldMFromToXYType = new FieldContainer<Integer>(TranslateAnimation.class, fieldMFromToXYTypeName);
        this.fieldMFromToXYValue = new FieldContainer<Float>(TranslateAnimation.class, fieldMFromToXYValueName);
    }

    @Override
    public void setAttribute(TranslateAnimation translateAnimation, DOMAttr attr, AttrAnimationContext attrCtx)
    {
        PercFloat convValue = getPercFloat(attr.getResourceDesc(),attrCtx.getXMLInflaterContext());

        int fromToXYType;
        float fomToXYValue;
        if (convValue.isFraction())
        {
            fromToXYType = convValue.isFractionParent() ? Animation.RELATIVE_TO_PARENT : Animation.RELATIVE_TO_SELF;
            fomToXYValue = convValue.toFloatBasedOnDataType();
        }
        else
        {
            fromToXYType = Animation.ABSOLUTE;
            fomToXYValue = convValue.toFloatBasedOnDataType();
        }

        fieldMFromToXYType.set(translateAnimation,fromToXYType);
        fieldMFromToXYValue.set(translateAnimation,fomToXYValue);
    }

    @Override
    public void removeAttribute(TranslateAnimation translateAnimation, AttrAnimationContext attrCtx)
    {
        setAttributeToRemove(translateAnimation, "0", attrCtx);
    }
}
