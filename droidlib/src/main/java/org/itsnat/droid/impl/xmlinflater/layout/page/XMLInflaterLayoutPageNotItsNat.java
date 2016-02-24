package org.itsnat.droid.impl.xmlinflater.layout.page;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.xmlinflated.layout.InflatedLayoutPageNotItsNatImpl;

/**
 * Created by jmarranz on 20/01/2016.
 */
public class XMLInflaterLayoutPageNotItsNat extends XMLInflaterLayoutPage
{
    public XMLInflaterLayoutPageNotItsNat(InflatedLayoutPageNotItsNatImpl inflatedXML, int bitmapDensityReference, AttrLayoutInflaterListener inflateLayoutListener, AttrDrawableInflaterListener attrDrawableInflaterListener,XMLDOMParserContext xmlDOMParserContext)
    {
        super(inflatedXML, bitmapDensityReference, inflateLayoutListener, attrDrawableInflaterListener,xmlDOMParserContext);
    }

    public InflatedLayoutPageNotItsNatImpl getInflatedLayoutPageNotItsNatImpl()
    {
        return (InflatedLayoutPageNotItsNatImpl) inflatedXML;
    }
}
