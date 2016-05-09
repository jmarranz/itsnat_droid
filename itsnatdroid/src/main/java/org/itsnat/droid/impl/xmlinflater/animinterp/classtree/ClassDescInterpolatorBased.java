package org.itsnat.droid.impl.xmlinflater.animinterp.classtree;

import android.view.animation.Interpolator;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.animinterp.DOMElemInterpolator;
import org.itsnat.droid.impl.xmlinflater.animinterp.AttrInterpolatorContext;
import org.itsnat.droid.impl.xmlinflater.animinterp.ClassDescInterpolatorMgr;
import org.itsnat.droid.impl.xmlinflater.animinterp.XMLInflaterInterpolator;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDescResourceBased;

import java.util.Map;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescInterpolatorBased<T extends Interpolator> extends ClassDescResourceBased<T,AttrInterpolatorContext>
{
    @SuppressWarnings("unchecked")
    public ClassDescInterpolatorBased(ClassDescInterpolatorMgr classMgr, String tagName, ClassDescInterpolatorBased parentClass)
    {
        super(classMgr, tagName, parentClass);
    }

    public ClassDescInterpolatorMgr getClassDescInterpolatorMgr()
    {
        return (ClassDescInterpolatorMgr) classMgr;
    }

    @SuppressWarnings("unchecked")
    public ClassDescInterpolatorBased<Interpolator> getParentClassDescInterpolatorBased()
    {
        return (ClassDescInterpolatorBased<Interpolator>) getParentClassDescResourceBased(); // Puede ser null
    }

    public abstract Class<T> getDeclaredClass();

    @Override
    protected void init()
    {
        //initClass();

        super.init();
    }

    public Interpolator createRootInterpolatorNativeAndFillAttributes(DOMElemInterpolator rootDOMElemInterpolator, AttrInterpolatorContext attrCtx)
    {
        XMLInflaterInterpolator xmlInflaterInterpolator = attrCtx.getXMLInflaterInterpolator();

        T rootInterpolator = createResourceNative(xmlInflaterInterpolator.getContext());
        xmlInflaterInterpolator.getInflatedInterpolator().setRootInterpolator(rootInterpolator); // Lo antes posible

        fillInterpolatorAttributes(rootInterpolator, rootDOMElemInterpolator, attrCtx);

        return rootInterpolator;
    }

    public Interpolator createInterpolatorNativeAndFillAttributes(DOMElemInterpolator domElement, AttrInterpolatorContext attrCtx)
    {
        XMLInflaterInterpolator xmlInflaterInterpolator = attrCtx.getXMLInflaterInterpolator();

        T interpolator = createResourceNative(xmlInflaterInterpolator.getContext());

        fillInterpolatorAttributes(interpolator, domElement, attrCtx);

        return interpolator;
    }

    protected void fillInterpolatorAttributes(T interpolator,DOMElemInterpolator domElement,AttrInterpolatorContext attrCtx)
    {
        Map<String,DOMAttr> attribMap = domElement.getDOMAttributes();
        if (attribMap != null)
        {
            for (Map.Entry<String,DOMAttr> entry : attribMap.entrySet())
            {
                DOMAttr attr = entry.getValue();
                setAttribute(interpolator, attr, attrCtx);
            }
        }
    }

}
