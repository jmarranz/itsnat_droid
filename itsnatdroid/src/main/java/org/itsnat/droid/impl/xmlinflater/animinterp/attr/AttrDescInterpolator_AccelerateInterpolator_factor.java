package org.itsnat.droid.impl.xmlinflater.animinterp.attr;

import android.view.animation.AccelerateInterpolator;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.animinterp.AttrInterpolatorContext;
import org.itsnat.droid.impl.xmlinflater.animinterp.classtree.ClassDescInterpolatorAccelerate;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescInterpolator_AccelerateInterpolator_factor extends AttrDesc<ClassDescInterpolatorAccelerate,AccelerateInterpolator,AttrInterpolatorContext>
{
    protected FieldContainer<Float> fieldMFactor;
    protected FieldContainer<Double> fieldMDoubleFactor;

    public AttrDescInterpolator_AccelerateInterpolator_factor(ClassDescInterpolatorAccelerate parent, String name)
    {
        super(parent,name);

        this.fieldMFactor = new FieldContainer<Float>(AccelerateInterpolator.class, "mFactor");
        this.fieldMDoubleFactor = new FieldContainer<Double>(AccelerateInterpolator.class, "mDoubleFactor");
    }

    @Override
    public void setAttribute(AccelerateInterpolator interpolator, DOMAttr attr, AttrInterpolatorContext attrCtx)
    {
        float convValue = getFloat(attr.getResourceDesc(),attrCtx.getXMLInflaterContext());

        fieldMFactor.set(interpolator,convValue);
        fieldMDoubleFactor.set(interpolator,(double)(convValue * 2));
    }

    @Override
    public void removeAttribute(AccelerateInterpolator interpolator, AttrInterpolatorContext attrCtx)
    {
        setAttributeToRemove(interpolator, "1.0", attrCtx);
    }
}
