package org.itsnat.droid.impl.xmlinflater.anim;

import android.view.animation.Animation;

import org.itsnat.droid.impl.xmlinflater.AttrResourceContext;

/**
 * Created by Jose on 09/11/2015.
 */
public class AttrAnimationContext extends AttrResourceContext<Animation>
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
