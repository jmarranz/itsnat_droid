package org.itsnat.droid.impl.xmlinflater.values;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.impl.browser.PageImpl;
import org.itsnat.droid.impl.xmlinflated.values.InflatedValuesPage;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterPage;

/**
 * Created by jmarranz on 24/11/14.
 */
public class XMLInflaterValuesPage extends XMLInflaterValues implements XMLInflaterPage
{
    public XMLInflaterValuesPage(InflatedValuesPage inflatedXML, int bitmapDensityReference, AttrLayoutInflaterListener attrLayoutInflaterListener, AttrDrawableInflaterListener attrDrawableInflaterListener)
    {
        super(inflatedXML,bitmapDensityReference,attrLayoutInflaterListener,attrDrawableInflaterListener);
    }

    public InflatedValuesPage getInflatedValuesPage()
    {
        return (InflatedValuesPage)getInflatedValues();
    }

    public PageImpl getPageImpl()
    {
        return getInflatedValuesPage().getPageImpl();
    }
}
