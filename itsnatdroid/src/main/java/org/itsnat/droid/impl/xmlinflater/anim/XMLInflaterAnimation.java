package org.itsnat.droid.impl.xmlinflater.anim;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import org.itsnat.droid.impl.dom.anim.DOMElemAnimation;
import org.itsnat.droid.impl.dom.anim.DOMElemAnimationSet;
import org.itsnat.droid.impl.dom.anim.XMLDOMAnimation;
import org.itsnat.droid.impl.xmlinflated.anim.InflatedAnimation;
import org.itsnat.droid.impl.xmlinflater.AttrInflaterListeners;
import org.itsnat.droid.impl.xmlinflater.XMLInflater;
import org.itsnat.droid.impl.xmlinflater.anim.classtree.ClassDescAnimationBased;

/**
 * Created by jmarranz on 4/11/14.
 */
public class XMLInflaterAnimation extends XMLInflater
{
    protected XMLInflaterAnimation(InflatedAnimation inflatedXML, int bitmapDensityReference, AttrInflaterListeners attrInflaterListeners)
    {
        super(inflatedXML, bitmapDensityReference, attrInflaterListeners);
    }

    public static XMLInflaterAnimation createXMLInflaterAnimation(InflatedAnimation inflatedAnimation, int bitmapDensityReference, AttrInflaterListeners attrInflaterListeners)
    {
        return new XMLInflaterAnimation(inflatedAnimation,bitmapDensityReference,attrInflaterListeners);
    }

    public ClassDescAnimationBased getClassDescAnimationBased(DOMElemAnimation domElemAnimation)
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

        ClassDescAnimationBased classDesc = getClassDescAnimationBased(rootDOMElem);
        Animation animationRoot = classDesc.createRootAnimationNativeAndFillAttributes(rootDOMElem, attrCtx);

        if (animationRoot instanceof AnimationSet)
            processChildElements((DOMElemAnimationSet)rootDOMElem, (AnimationSet)animationRoot,attrCtx);

        return animationRoot;
    }

    private void processChildElements(DOMElemAnimationSet domElemParent, AnimationSet parentAnimation, AttrAnimationContext attrCtx)
    {
        /*
        LinkedList<DOMElement> childDOMElemList = domElemParent.getChildDOMElementList();
        if (childDOMElemList == null || childDOMElemList.size() == 0) return;

        String ordering = ClassDescAnimationSet.getOrderingAttribute(domElemParent);

        Animation[] childAnimations = new Animation[childDOMElemList.size()];
        int i = 0;
        for (DOMElement childDOMElem : childDOMElemList)
        {
            Animation animation = inflateNextElement((DOMElemAnimation)childDOMElem,attrCtx);
            childAnimations[i] = animation;
            i++;
        }

        if ("together".equals(ordering))
            parentAnimation.playTogether(childAnimations);
        else if ("sequentially".equals(ordering))
            parentAnimation.playSequentially(childAnimations);
        else
            throw new ItsNatDroidException("Unknown ordering attribute value: " + ordering);

            */
    }

    protected Animation inflateNextElement(DOMElemAnimation domElement,AttrAnimationContext attrCtx)
    {
        ClassDescAnimationBased classDesc = getClassDescAnimationBased(domElement);
        Animation animation = classDesc.createAnimationNativeAndFillAttributes(domElement, attrCtx);

        if (animation instanceof AnimationSet)
            processChildElements((DOMElemAnimationSet)domElement, (AnimationSet)animation,attrCtx);

        return animation;
    }
}
