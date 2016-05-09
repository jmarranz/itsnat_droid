package org.itsnat.droid.impl.xmlinflater.layout.page;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageNotItsNatImpl;

/**
 * Created by jmarranz on 20/01/2016.
 */
public class XMLInflaterLayoutPageNotItsNat extends XMLInflaterLayoutPage
{
    public XMLInflaterLayoutPageNotItsNat(InflatedLayoutPageNotItsNatImpl inflatedXML, int bitmapDensityReference,AttrResourceInflaterListener attrResourceInflaterListener)
    {
        super(inflatedXML, bitmapDensityReference, attrResourceInflaterListener);
    }

    public InflatedLayoutPageNotItsNatImpl getInflatedLayoutPageNotItsNatImpl()
    {
        return (InflatedLayoutPageNotItsNatImpl) inflatedXML;
    }
}
