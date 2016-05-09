package org.itsnat.droid.impl.xmlinflater.anim.classtree;

import android.view.animation.Animation;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.anim.DOMElemAnimation;
import org.itsnat.droid.impl.xmlinflater.anim.AttrAnimationContext;
import org.itsnat.droid.impl.xmlinflater.anim.ClassDescAnimationMgr;
import org.itsnat.droid.impl.xmlinflater.anim.XMLInflaterAnimation;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDescResourceBased;

import java.util.Map;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescAnimationBased<T extends Animation> extends ClassDescResourceBased<T,AttrAnimationContext>
{
    @SuppressWarnings("unchecked")
    public ClassDescAnimationBased(ClassDescAnimationMgr classMgr, String tagName, ClassDescAnimationBased<? super T> parentClass)
    {
        super(classMgr, tagName, parentClass);
    }

    public ClassDescAnimationMgr getClassDescAnimationMgr()
    {
        return (ClassDescAnimationMgr) classMgr;
    }

    @SuppressWarnings("unchecked")
    public ClassDescAnimationBased<Animation> getParentClassDescAnimationBased()
    {
        return (ClassDescAnimationBased<Animation>) getParentClassDescResourceBased(); // Puede ser null
    }

    @Override
    protected void init()
    {
        //initClass();

        super.init();
    }

    public Animation createRootAnimationNativeAndFillAttributes(DOMElemAnimation rootDOMElemAnimation, AttrAnimationContext attrCtx)
    {
        XMLInflaterAnimation xmlInflaterAnimation = attrCtx.getXMLInflaterAnimation();

        T rootAnimation = createResourceNative(xmlInflaterAnimation.getContext());
        xmlInflaterAnimation.getInflatedAnimation().setRootAnimation(rootAnimation); // Lo antes posible

        fillAnimationAttributes(rootAnimation, rootDOMElemAnimation, attrCtx);

        return rootAnimation;
    }

    public Animation createAnimationNativeAndFillAttributes(DOMElemAnimation domElement, AttrAnimationContext attrCtx)
    {
        XMLInflaterAnimation xmlInflaterAnimation = attrCtx.getXMLInflaterAnimation();

        T animation = createResourceNative(xmlInflaterAnimation.getContext());

        fillAnimationAttributes(animation, domElement, attrCtx);

        return animation;
    }

    protected void fillAnimationAttributes(T animation,DOMElemAnimation domElement,AttrAnimationContext attrCtx)
    {
        Map<String,DOMAttr> attribMap = domElement.getDOMAttributes();
        if (attribMap != null)
        {
            for (Map.Entry<String,DOMAttr> entry : attribMap.entrySet())
            {
                DOMAttr attr = entry.getValue();
                setAttribute(animation, attr, attrCtx);
            }
        }
    }


}
