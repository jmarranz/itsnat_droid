package org.itsnat.droid.impl.xmlinflater.layout.page;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedXMLLayoutPageNotItsNatImpl;

/**
 * Created by jmarranz on 20/01/2016.
 */
public class XMLInflaterLayoutPageNotItsNat extends XMLInflaterLayoutPage
{
    public XMLInflaterLayoutPageNotItsNat(InflatedXMLLayoutPageNotItsNatImpl inflatedXML, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        super(inflatedXML, bitmapDensityReference, attrResourceInflaterListener);
    }

    public InflatedXMLLayoutPageNotItsNatImpl getInflatedXMLLayoutPageNotItsNatImpl()
    {
        return (InflatedXMLLayoutPageNotItsNatImpl) inflatedXML;
    }
}
