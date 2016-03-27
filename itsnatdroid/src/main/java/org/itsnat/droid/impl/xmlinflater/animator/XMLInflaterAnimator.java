package org.itsnat.droid.impl.xmlinflater.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.animator.DOMElemAnimator;
import org.itsnat.droid.impl.dom.animator.DOMElemAnimatorSet;
import org.itsnat.droid.impl.dom.animator.XMLDOMAnimator;
import org.itsnat.droid.impl.xmlinflated.animator.InflatedAnimator;
import org.itsnat.droid.impl.xmlinflater.AttrInflaterListeners;
import org.itsnat.droid.impl.xmlinflater.XMLInflater;
import org.itsnat.droid.impl.xmlinflater.animator.classtree.ClassDescAnimatorBased;
import org.itsnat.droid.impl.xmlinflater.animator.classtree.ClassDescAnimatorSet;

import java.util.LinkedList;

/**
 * Created by jmarranz on 4/11/14.
 */
public class XMLInflaterAnimator extends XMLInflater
{
    protected XMLInflaterAnimator(InflatedAnimator inflatedXML, int bitmapDensityReference,AttrInflaterListeners attrInflaterListeners)
    {
        super(inflatedXML, bitmapDensityReference, attrInflaterListeners);
    }

    public static XMLInflaterAnimator createXMLInflaterAnimator(InflatedAnimator inflatedAnimator,int bitmapDensityReference,AttrInflaterListeners attrInflaterListeners)
    {
        return new XMLInflaterAnimator(inflatedAnimator,bitmapDensityReference,attrInflaterListeners);
    }

    public ClassDescAnimatorBased getClassDescAnimatorBased(DOMElemAnimator domElemAnimator)
    {
        ClassDescAnimatorMgr classDescMgr = getInflatedAnimator().getXMLInflateRegistry().getClassDescAnimatorMgr();
        return classDescMgr.get(domElemAnimator.getTagName());
    }

    public InflatedAnimator getInflatedAnimator()
    {
        return (InflatedAnimator)inflatedXML;
    }

    public Animator inflateAnimator()
    {
        return inflateRoot(getInflatedAnimator().getXMLDOMAnimator());
    }

    private Animator inflateRoot(XMLDOMAnimator xmlDOMAnimator)
    {
        DOMElemAnimator rootDOMElem = (DOMElemAnimator)xmlDOMAnimator.getRootDOMElement();

        AttrAnimatorContext attrCtx = new AttrAnimatorContext(this);

        ClassDescAnimatorBased classDesc = getClassDescAnimatorBased(rootDOMElem);
        Animator animatorRoot = classDesc.createRootAnimatorNativeAndFillAttributes(rootDOMElem, attrCtx);

        if (animatorRoot instanceof AnimatorSet)
            processChildElements((DOMElemAnimatorSet)rootDOMElem, (AnimatorSet)animatorRoot,attrCtx);

        return animatorRoot;
    }

    private void processChildElements(DOMElemAnimatorSet domElemParent, AnimatorSet parentAnimator,AttrAnimatorContext attrCtx)
    {
        LinkedList<DOMElement> childDOMElemList = domElemParent.getChildDOMElementList();
        if (childDOMElemList == null || childDOMElemList.size() == 0) return;

        String ordering = ClassDescAnimatorSet.getOrderingAttribute(domElemParent);

        Animator[] childAnimators = new Animator[childDOMElemList.size()];
        int i = 0;
        for (DOMElement childDOMElem : childDOMElemList)
        {
            Animator animator = inflateNextElement((DOMElemAnimator)childDOMElem,attrCtx);
            childAnimators[i] = animator;
            i++;
        }

        if ("together".equals(ordering))
            parentAnimator.playTogether(childAnimators);
        else if ("sequentially".equals(ordering))
            parentAnimator.playSequentially(childAnimators);
        else
            throw new ItsNatDroidException("Unknown ordering attribute value: " + ordering);
    }

    protected Animator inflateNextElement(DOMElemAnimator domElement,AttrAnimatorContext attrCtx)
    {
        ClassDescAnimatorBased classDesc = getClassDescAnimatorBased(domElement);
        Animator animator = classDesc.createAnimatorNativeAndFillAttributes(domElement, attrCtx);

        if (animator instanceof AnimatorSet)
            processChildElements((DOMElemAnimatorSet)domElement, (AnimatorSet)animator,attrCtx);

        return animator;
    }
}
