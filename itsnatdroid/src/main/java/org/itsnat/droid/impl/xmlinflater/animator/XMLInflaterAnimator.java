package org.itsnat.droid.impl.xmlinflater.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.animator.DOMElemAnimator;
import org.itsnat.droid.impl.dom.animator.DOMElemAnimatorSet;
import org.itsnat.droid.impl.dom.animator.XMLDOMAnimator;
import org.itsnat.droid.impl.xmlinflated.animator.InflatedXMLAnimator;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterResource;
import org.itsnat.droid.impl.xmlinflater.animator.classtree.ClassDescAnimatorBased;
import org.itsnat.droid.impl.xmlinflater.animator.classtree.ClassDescAnimatorSet;

import java.util.List;

/**
 * Created by jmarranz on 4/11/14.
 */
public class XMLInflaterAnimator extends XMLInflaterResource<Animator>
{
    protected XMLInflaterAnimator(InflatedXMLAnimator inflatedXML, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        super(inflatedXML, bitmapDensityReference, attrResourceInflaterListener);
    }

    public static XMLInflaterAnimator createXMLInflaterAnimator(InflatedXMLAnimator inflatedAnimator, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        return new XMLInflaterAnimator(inflatedAnimator,bitmapDensityReference,attrResourceInflaterListener);
    }

    @SuppressWarnings("unchecked")
    public ClassDescAnimatorBased<Animator> getClassDescAnimatorBased(DOMElemAnimator domElemAnimator)
    {
        ClassDescAnimatorMgr classDescMgr = getInflatedXMLAnimator().getXMLInflaterRegistry().getClassDescAnimatorMgr();
        return classDescMgr.get(domElemAnimator.getTagName());
    }

    public InflatedXMLAnimator getInflatedXMLAnimator()
    {
        return (InflatedXMLAnimator)inflatedXML;
    }

    public Animator inflateAnimator()
    {
        return inflateRoot(getInflatedXMLAnimator().getXMLDOMAnimator());
    }

    private Animator inflateRoot(XMLDOMAnimator xmlDOMAnimator)
    {
        DOMElemAnimator rootDOMElem = (DOMElemAnimator)xmlDOMAnimator.getRootDOMElement();

        AttrAnimatorContext attrCtx = new AttrAnimatorContext(this);

        ClassDescAnimatorBased<Animator> classDesc = getClassDescAnimatorBased(rootDOMElem);
        Animator animatorRoot = classDesc.createRootResourceAndFillAttributes(rootDOMElem, attrCtx);

        if (animatorRoot instanceof AnimatorSet)
            processChildElements((DOMElemAnimatorSet)rootDOMElem, (AnimatorSet)animatorRoot,attrCtx);

        // En teoría <objectAnimator> y <animator> podrían tener elementos hijo tal y como
        // <objectAnimator>
        //     <propertyValuesHolder android:propertyName="x" android:valueTo="400"/>
        //     <propertyValuesHolder android:propertyName="y" android:valueTo="200"/>
        // <animator>
        //      <keyframe android:fraction="0" android:value="1"/>
        //      <keyframe android:fraction=".2" android:value=".4"/>
        //      <keyframe android:fraction="1" android:value="0"/>
        // pudiéndose declarar varias propiedades a la vez y animarlas a la vez.
        // Sin embargo este XML fue introducido en Android 5.0 Lollipop, aunque es posible hacerlo de forma programática usando
        // ValueAnimator.setValues(PropertyValuesHolder... values)
        // http://developer.android.com/reference/android/animation/ObjectAnimator.html
        // http://developer.android.com/reference/android/animation/ValueAnimator.html
        // http://stackoverflow.com/questions/32885324/propertyvaluesholder-causes-crash-when-used-in-xml-defined-animation

        return animatorRoot;
    }

    private void processChildElements(DOMElemAnimatorSet domElemParent, AnimatorSet parentAnimator,AttrAnimatorContext attrCtx)
    {
        List<DOMElement> childDOMElemList = domElemParent.getChildDOMElementList();
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
        ClassDescAnimatorBased<Animator> classDesc = getClassDescAnimatorBased(domElement);
        Animator animator = classDesc.createResourceAndFillAttributes(domElement, attrCtx);

        if (animator instanceof AnimatorSet)
            processChildElements((DOMElemAnimatorSet)domElement, (AnimatorSet)animator,attrCtx);

        return animator;
    }
}
