package org.itsnat.droid.impl.xmlinflater.animinterp;

import android.view.animation.Interpolator;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.impl.dom.animinterp.DOMElemInterpolator;
import org.itsnat.droid.impl.dom.animinterp.XMLDOMInterpolator;
import org.itsnat.droid.impl.xmlinflated.animinterp.InflatedInterpolator;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterResource;
import org.itsnat.droid.impl.xmlinflater.animinterp.classtree.ClassDescInterpolatorBased;

/**
 * Created by jmarranz on 4/11/14.
 */
public class XMLInflaterInterpolator extends XMLInflaterResource<Interpolator>
{
    protected XMLInflaterInterpolator(InflatedInterpolator inflatedXML, int bitmapDensityReference,AttrResourceInflaterListener attrResourceInflaterListener)
    {
        super(inflatedXML, bitmapDensityReference, attrResourceInflaterListener);
    }

    public static XMLInflaterInterpolator createXMLInflaterInterpolator(InflatedInterpolator inflatedInterpolator, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        return new XMLInflaterInterpolator(inflatedInterpolator,bitmapDensityReference,attrResourceInflaterListener);
    }

    @SuppressWarnings("unchecked")
    public ClassDescInterpolatorBased<Interpolator> getClassDescInterpolatorBased(DOMElemInterpolator domElemInterpolator)
    {
        ClassDescInterpolatorMgr classDescMgr = getInflatedInterpolator().getXMLInflaterRegistry().getClassDescInterpolatorMgr();
        return classDescMgr.get(domElemInterpolator.getTagName());
    }

    public InflatedInterpolator getInflatedInterpolator()
    {
        return (InflatedInterpolator)inflatedXML;
    }

    public Interpolator inflateInterpolator()
    {
        return inflateRoot(getInflatedInterpolator().getXMLDOMInterpolator());
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
