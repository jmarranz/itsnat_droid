package org.itsnat.droid.impl.xmlinflater.animinterp;

import android.view.animation.Interpolator;
import org.itsnat.droid.impl.xmlinflater.AttrResourceContext;

/**
 * Created by Jose on 09/11/2015.
 */
public class AttrInterpolatorContext extends AttrResourceContext<Interpolator>
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
