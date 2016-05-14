package org.itsnat.droid.impl.xmlinflater.animinterp.classtree;

import android.content.Context;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterResource;
import org.itsnat.droid.impl.xmlinflater.animinterp.AttrInterpolatorContext;
import org.itsnat.droid.impl.xmlinflater.animinterp.ClassDescInterpolatorMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetFloat;

/**
 * Created by Jose on 15/10/2015.
 */
public class ClassDescInterpolatorAnticipateOvershoot extends ClassDescInterpolatorBased<AnticipateOvershootInterpolator>
{
    protected FieldContainer<Float> fieldMTension;

    public ClassDescInterpolatorAnticipateOvershoot(ClassDescInterpolatorMgr classMgr)
    {
        super(classMgr, "anticipateOvershootInterpolator", null);

        this.fieldMTension = new FieldContainer<Float>(AnticipateOvershootInterpolator.class,"mTension");
    }

    @Override
    public Class<AnticipateOvershootInterpolator> getDeclaredClass()
    {
        return AnticipateOvershootInterpolator.class;
    }

    @Override
    protected AnticipateOvershootInterpolator createResourceNative(Context ctx)
    {
        return new AnticipateOvershootInterpolator(ctx,null);
    }

    protected boolean isAttributeIgnored(String namespaceURI, String name)
    {
        if (super.isAttributeIgnored(namespaceURI,name))
            return true;
        return NamespaceUtil.XMLNS_ANDROID.equals(namespaceURI) && (name.equals("tension") || name.equals("extraTension"));
    }


    protected void fillResourceAttributes(AnticipateOvershootInterpolator interpolator, DOMElement domElement, AttrInterpolatorContext attrCtx)
    {
        XMLInflaterResource xmlInflaterInterpolator = attrCtx.getXMLInflaterResource();
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflaterInterpolator.getInflatedResource().getXMLInflaterRegistry();

        DOMAttr attrTension = domElement.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "tension");
        float tension = attrTension != null ? xmlInflaterRegistry.getFloat(attrTension.getResourceDesc(),attrCtx.getXMLInflaterContext()) : 2.0f;

        DOMAttr attrExtraTension = domElement.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, "extraTension");
        float extraTension = attrExtraTension != null ? xmlInflaterRegistry.getFloat(attrExtraTension.getResourceDesc(),attrCtx.getXMLInflaterContext()) : 1.5f;

        float totalTension = tension * extraTension;

        fieldMTension.set(interpolator,totalTension);

        super.fillResourceAttributes(interpolator,domElement,attrCtx);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

    }
}

