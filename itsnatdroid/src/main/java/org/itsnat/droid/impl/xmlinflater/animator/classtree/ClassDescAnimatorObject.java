package org.itsnat.droid.impl.xmlinflater.animator.classtree;

import android.animation.ObjectAnimator;
import android.content.Context;

import org.itsnat.droid.impl.dom.animator.DOMElemAnimator;
import org.itsnat.droid.impl.xmlinflater.animator.AttrAnimatorContext;
import org.itsnat.droid.impl.xmlinflater.animator.ClassDescAnimatorMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodString;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescAnimatorObject extends ClassDescAnimatorBased<ObjectAnimator>
{
    public ClassDescAnimatorObject(ClassDescAnimatorMgr classMgr, ClassDescAnimatorValue parentClass)
    {
        super(classMgr, "objectAnimator", parentClass);
    }

    @Override
    public Class<ObjectAnimator> getDeclaredClass()
    {
        return ObjectAnimator.class;
    }

    @Override
    protected ObjectAnimator createResourceNative(Context ctx)
    {
        return new ObjectAnimator();
    }

    protected void fillAnimatorAttributes(ObjectAnimator animator,DOMElemAnimator domElement,AttrAnimatorContext attrCtx)
    {
        ((ClassDescAnimatorValue)parentClass).fillAnimatorValueConstructionAttributes(animator, domElement, attrCtx);

        super.fillAnimatorAttributes(animator, domElement,attrCtx);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodString(this, "propertyName", null)); // Si se ve el código fuente de setPropertyName(String) se ve que se puede llamar antes o después de definir el valueFrom y valueTo
    }
}
