package org.itsnat.droid.impl.xmlinflater.animator;

import org.itsnat.droid.impl.xmlinflater.AttrContext;

/**
 * Created by Jose on 09/11/2015.
 */
public class AttrAnimatorContext extends AttrContext
{
    public AttrAnimatorContext(XMLInflaterAnimator xmlInflater)
    {
        super(xmlInflater);
    }

    public XMLInflaterAnimator getXMLInflaterAnimator()
    {
        return (XMLInflaterAnimator)xmlInflater;
    }
}
