package org.itsnat.droid.impl.xmlinflater.layout.page;

import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageNotItsNatImpl;
import org.itsnat.droid.impl.xmlinflater.AttrInflaterListeners;

/**
 * Created by jmarranz on 20/01/2016.
 */
public class XMLInflaterLayoutPageNotItsNat extends XMLInflaterLayoutPage
{
    public XMLInflaterLayoutPageNotItsNat(InflatedLayoutPageNotItsNatImpl inflatedXML, int bitmapDensityReference,AttrInflaterListeners attrInflaterListeners)
    {
        super(inflatedXML, bitmapDensityReference, attrInflaterListeners);
    }

    public InflatedLayoutPageNotItsNatImpl getInflatedLayoutPageNotItsNatImpl()
    {
        return (InflatedLayoutPageNotItsNatImpl) inflatedXML;
    }
}
