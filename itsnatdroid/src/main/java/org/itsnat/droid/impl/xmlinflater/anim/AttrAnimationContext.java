package org.itsnat.droid.impl.xmlinflater.anim;

import org.itsnat.droid.impl.xmlinflater.AttrContext;

/**
 * Created by Jose on 09/11/2015.
 */
public class AttrAnimationContext extends AttrContext
{
    public AttrAnimationContext(XMLInflaterAnimation xmlInflater)
    {
        super(xmlInflater);
    }

    public XMLInflaterAnimation getXMLInflaterAnimation()
    {
        return (XMLInflaterAnimation)xmlInflater;
    }
}
