package org.itsnat.droid.impl.xmlinflater.animinterp;

import android.view.animation.Interpolator;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.impl.dom.animinterp.DOMElemInterpolator;
import org.itsnat.droid.impl.dom.animinterp.XMLDOMInterpolator;
import org.itsnat.droid.impl.xmlinflated.animinterp.InflatedXMLInterpolator;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterResource;
import org.itsnat.droid.impl.xmlinflater.animinterp.classtree.ClassDescInterpolatorBased;

/**
 * Created by jmarranz on 4/11/14.
 */
public class XMLInflaterInterpolator extends XMLInflaterResource<Interpolator>
{
    protected XMLInflaterInterpolator(InflatedXMLInterpolator inflatedXML, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        super(inflatedXML, bitmapDensityReference, attrResourceInflaterListener);
    }

    public static XMLInflaterInterpolator createXMLInflaterInterpolator(InflatedXMLInterpolator inflatedInterpolator, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        return new XMLInflaterInterpolator(inflatedInterpolator,bitmapDensityReference,attrResourceInflaterListener);
    }

    @SuppressWarnings("unchecked")
    public ClassDescInterpolatorBased<Interpolator> getClassDescInterpolatorBased(DOMElemInterpolator domElemInterpolator)
    {
        ClassDescInterpolatorMgr classDescMgr = getInflatedXMLInterpolator().getXMLInflaterRegistry().getClassDescInterpolatorMgr();
        return classDescMgr.get(domElemInterpolator.getTagName());
    }

    public InflatedXMLInterpolator getInflatedXMLInterpolator()
    {
        return (InflatedXMLInterpolator)inflatedXML;
    }

    public Interpolator inflateInterpolator()
    {
        return inflateRoot(getInflatedXMLInterpolator().getXMLDOMInterpolator());
    }

    private Interpolator inflateRoot(XMLDOMInterpolator xmlDOMInterpolator)
    {
        DOMElemInterpolator rootDOMElem = (DOMElemInterpolator)xmlDOMInterpolator.getRootDOMElement();

        AttrInterpolatorContext attrCtx = new AttrInterpolatorContext(this);

        ClassDescInterpolatorBased<Interpolator> classDesc = getClassDescInterpolatorBased(rootDOMElem);
        Interpolator interpolatorRoot = classDesc.createRootResourceAndFillAttributes(rootDOMElem, attrCtx);
        // NO hay hijos
        return interpolatorRoot;
    }

}
