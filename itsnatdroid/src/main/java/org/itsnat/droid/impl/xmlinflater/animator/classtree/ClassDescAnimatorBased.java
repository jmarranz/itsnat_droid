package org.itsnat.droid.impl.xmlinflater.animator.classtree;

import android.animation.Animator;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.animator.DOMElemAnimator;
import org.itsnat.droid.impl.xmlinflater.animator.AttrAnimatorContext;
import org.itsnat.droid.impl.xmlinflater.animator.ClassDescAnimatorMgr;
import org.itsnat.droid.impl.xmlinflater.animator.XMLInflaterAnimator;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDescResourceBased;

import java.util.Map;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescAnimatorBased<T extends Animator> extends ClassDescResourceBased<T,AttrAnimatorContext>
{
    @SuppressWarnings("unchecked")
    public ClassDescAnimatorBased(ClassDescAnimatorMgr classMgr, String tagName, ClassDescAnimatorBased<? super T> parentClass)
    {
        super(classMgr, tagName, parentClass);
    }

    public ClassDescAnimatorMgr getClassDescAnimatorMgr()
    {
        return (ClassDescAnimatorMgr) classMgr;
    }

    @SuppressWarnings("unchecked")
    public ClassDescAnimatorBased<Animator> getParentClassDescAnimatorBased()
    {
        return (ClassDescAnimatorBased<Animator>) getParentClassDescResourceBased(); // Puede ser null
    }


    @Override
    protected void init()
    {
        //initClass();

        super.init();
    }

    public Animator createRootAnimatorNativeAndFillAttributes(DOMElemAnimator rootDOMElemAnimator, AttrAnimatorContext attrCtx)
    {
        XMLInflaterAnimator xmlInflaterAnimator = attrCtx.getXMLInflaterAnimator();

        T rootAnimator = createResourceNative(xmlInflaterAnimator.getContext());
        xmlInflaterAnimator.getInflatedAnimator().setRootAnimator(rootAnimator); // Lo antes posible

        fillAnimatorAttributes(rootAnimator, rootDOMElemAnimator, attrCtx);

        return rootAnimator;
    }

    public Animator createAnimatorNativeAndFillAttributes(DOMElemAnimator domElement, AttrAnimatorContext attrCtx)
    {
        XMLInflaterAnimator xmlInflaterAnimator = attrCtx.getXMLInflaterAnimator();

        T animator = createResourceNative(xmlInflaterAnimator.getContext());

        fillAnimatorAttributes(animator, domElement, attrCtx);

        return animator;
    }


    protected void fillAnimatorAttributes(T animator,DOMElemAnimator domElement,AttrAnimatorContext attrCtx)
    {
        Map<String,DOMAttr> attribMap = domElement.getDOMAttributes();
        if (attribMap != null)
        {
            for (Map.Entry<String,DOMAttr> entry : attribMap.entrySet())
            {
                DOMAttr attr = entry.getValue();
                setAttribute(animator, attr, attrCtx);
            }
        }
    }

}
