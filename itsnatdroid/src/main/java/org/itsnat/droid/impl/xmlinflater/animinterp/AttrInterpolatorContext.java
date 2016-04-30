package org.itsnat.droid.impl.xmlinflater.animinterp;

import org.itsnat.droid.impl.xmlinflater.AttrContext;

/**
 * Created by Jose on 09/11/2015.
 */
public class AttrInterpolatorContext extends AttrContext
{
    public AttrInterpolatorContext(XMLInflaterInterpolator xmlInflater)
    {
        super(xmlInflater);
    }

    public XMLInflaterInterpolator getXMLInflaterInterpolator()
    {
        return (XMLInflaterInterpolator)xmlInflater;
    }
}
