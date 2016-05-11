package org.itsnat.droid.impl.xmlinflater.animlayout;

import android.view.animation.LayoutAnimationController;

import org.itsnat.droid.impl.xmlinflater.AttrResourceContext;

/**
 * Created by Jose on 09/11/2015.
 */
public class AttrLayoutAnimationContext extends AttrResourceContext<LayoutAnimationController>
{
    public AttrLayoutAnimationContext(XMLInflaterLayoutAnimation xmlInflater)
    {
        super(xmlInflater);
    }

    public XMLInflaterLayoutAnimation getXMLInflaterLayoutAnimation()
    {
        return (XMLInflaterLayoutAnimation)xmlInflater;
    }
}
