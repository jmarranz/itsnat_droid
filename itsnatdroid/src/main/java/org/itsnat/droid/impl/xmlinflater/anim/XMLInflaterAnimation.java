package org.itsnat.droid.impl.xmlinflater.anim;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.anim.DOMElemAnimation;
import org.itsnat.droid.impl.dom.anim.DOMElemAnimationSet;
import org.itsnat.droid.impl.dom.anim.XMLDOMAnimation;
import org.itsnat.droid.impl.xmlinflated.anim.InflatedAnimation;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterResource;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimationBased;

import java.util.List;

/**
 * Created by jmarranz on 4/11/14.
 */
public class XMLInflaterAnimation extends XMLInflaterResource<Animation>
{
    protected XMLInflaterAnimation(InflatedAnimation inflatedXML, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        super(inflatedXML, bitmapDensityReference, attrResourceInflaterListener);
    }

    public static XMLInflaterAnimation createXMLInflaterAnimation(InflatedAnimation inflatedAnimation, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        return new XMLInflaterAnimation(inflatedAnimation,bitmapDensityReference,attrResourceInflaterListener);
    }

    @SuppressWarnings("unchecked")
    public ClassDescAnimationBased<Animation> getClassDescAnimationBased(DOMElemAnimation domElemAnimation)
    {
        ClassDescAnimationMgr classDescMgr = getInflatedAnimation().getXMLInflaterRegistry().getClassDescAnimationMgr();
        return classDescMgr.get(domElemAnimation.getTagName());
    }

    public InflatedAnimation getInflatedAnimation()
    {
        return (InflatedAnimation)inflatedXML;
    }

    public Animation inflateAnimation()
    {
        return inflateRoot(getInflatedAnimation().getXMLDOMAnimation());
    }

    private Animation inflateRoot(XMLDOMAnimation xmlDOMAnimation)
    {
        DOMElemAnimation rootDOMElem = (DOMElemAnimation)xmlDOMAnimation.getRootDOMElement();

        AttrAnimationContext attrCtx = new AttrAnimationContext(this);

        ClassDescAnimationBased<Animation> classDesc = getClassDescAnimationBased(rootDOMElem);
        Animation animationRoot = classDesc.createRootResourceAndFillAttributes(rootDOMElem, attrCtx);

        // No te creas t_odo lo que viene en la doc de Android, cualquier Animation puede ser root no sólo <set>
        // http://developerlife.com/tutorials/?p=343 (ejemplo <alpha>)
        // <alpha xmlns:android="http://schemas.android.com/apk/res/android"
        //        android:interpolator="@android:anim/accelerate_interpolator"
        //        android:fromAlpha="0.0" android:toAlpha="1.0" android:duration="100" />

        if (animationRoot instanceof AnimationSet)
            processChildElements((DOMElemAnimationSet)rootDOMElem,(AnimationSet)animationRoot,attrCtx);

        return animationRoot;
    }

    private void processChildElements(DOMElemAnimationSet domElemParent, AnimationSet parentAnimation, AttrAnimationContext attrCtx)
    {
        List<DOMElement> childDOMElemList = domElemParent.getChildDOMElementList();
        if (childDOMElemList == null || childDOMElemList.size() == 0) return;

        for (DOMElement childDOMElem : childDOMElemList)
        {
            Animation animation = inflateNextElement((DOMElemAnimation)childDOMElem,attrCtx);
            parentAnimation.addAnimation(animation);
        }
    }

    protected Animation inflateNextElement(DOMElemAnimation domElement,AttrAnimationContext attrCtx)
    {
        ClassDescAnimationBased<Animation> classDesc = getClassDescAnimationBased(domElement);
        Animation animation = classDesc.createResourceAndFillAttributes(domElement, attrCtx);

        if (animation instanceof AnimationSet)
            processChildElements((DOMElemAnimationSet)domElement, (AnimationSet)animation,attrCtx);

        return animation;
    }
}
