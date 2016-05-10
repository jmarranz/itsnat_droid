package org.itsnat.droid.impl.xmlinflater.animator;

import android.animation.Animator;
import org.itsnat.droid.impl.xmlinflater.AttrResourceContext;

/**
 * Created by Jose on 09/11/2015.
 */
public class AttrAnimatorContext extends AttrResourceContext<Animator>
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
