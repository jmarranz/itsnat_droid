package org.itsnat.droid.impl.xmlinflater.animlayout;

import android.view.animation.LayoutAnimationController;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.impl.dom.animlayout.DOMElemLayoutAnimation;
import org.itsnat.droid.impl.dom.animlayout.XMLDOMLayoutAnimation;
import org.itsnat.droid.impl.xmlinflated.animlayout.InflatedXMLLayoutAnimation;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterResource;
import org.itsnat.droid.impl.xmlinflater.animlayout.classtree.ClassDescLayoutAnimationBased;

/**
 * Created by jmarranz on 4/11/14.
 */
public class XMLInflaterLayoutAnimation extends XMLInflaterResource<LayoutAnimationController>
{
    protected XMLInflaterLayoutAnimation(InflatedXMLLayoutAnimation inflatedXML, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        super(inflatedXML, bitmapDensityReference, attrResourceInflaterListener);
    }

    public static XMLInflaterLayoutAnimation createXMLInflaterLayoutAnimation(InflatedXMLLayoutAnimation inflatedLayoutAnimation, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        return new XMLInflaterLayoutAnimation(inflatedLayoutAnimation,bitmapDensityReference,attrResourceInflaterListener);
    }

    @SuppressWarnings("unchecked")
    public ClassDescLayoutAnimationBased<LayoutAnimationController> getClassDescLayoutAnimationBased(DOMElemLayoutAnimation domElemLayoutAnimation)
    {
        ClassDescLayoutAnimationMgr classDescMgr = getInflatedXMLLayoutAnimation().getXMLInflaterRegistry().getClassDescLayoutAnimationMgr();
        return classDescMgr.get(domElemLayoutAnimation.getTagName());
    }

    public InflatedXMLLayoutAnimation getInflatedXMLLayoutAnimation()
    {
        return (InflatedXMLLayoutAnimation)inflatedXML;
    }

    public LayoutAnimationController inflateLayoutAnimation()
    {
        return inflateRoot(getInflatedXMLLayoutAnimation().getXMLDOMLayoutAnimation());
    }

    private LayoutAnimationController inflateRoot(XMLDOMLayoutAnimation xmlDOMLayoutAnimation)
    {
        DOMElemLayoutAnimation rootDOMElem = (DOMElemLayoutAnimation)xmlDOMLayoutAnimation.getRootDOMElement();

        AttrLayoutAnimationContext attrCtx = new AttrLayoutAnimationContext(this);

        ClassDescLayoutAnimationBased<LayoutAnimationController> classDesc = getClassDescLayoutAnimationBased(rootDOMElem);
        LayoutAnimationController animationRoot = classDesc.createRootResourceAndFillAttributes(rootDOMElem, attrCtx);

        // NO HAY HIJOS

        return animationRoot;
    }

}
