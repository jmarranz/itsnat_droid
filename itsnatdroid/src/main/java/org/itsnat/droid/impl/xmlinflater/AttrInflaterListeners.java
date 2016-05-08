package org.itsnat.droid.impl.xmlinflater;

import org.itsnat.droid.AttrAnimationInflaterListener;
import org.itsnat.droid.AttrAnimatorInflaterListener;
import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrInterpolatorInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.AttrResourceInflaterListener;

/**
 * Created by jmarranz on 22/03/2016.
 */
public class AttrInflaterListeners
{
    private final AttrLayoutInflaterListener attrLayoutInflaterListener;
    private final AttrDrawableInflaterListener attrDrawableInflaterListener;
    private final AttrAnimationInflaterListener attrAnimationInflaterListener;
    private final AttrAnimatorInflaterListener attrAnimatorInflaterListener;
    private final AttrInterpolatorInflaterListener attrInterpolatorInflaterListener;
    private final AttrResourceInflaterListener attrResourceInflaterListener;

    public AttrInflaterListeners(AttrLayoutInflaterListener attrLayoutInflaterListener, AttrDrawableInflaterListener attrDrawableInflaterListener,
                                 AttrAnimationInflaterListener attrAnimationInflaterListener, AttrAnimatorInflaterListener attrAnimatorInflaterListener,
                                 AttrInterpolatorInflaterListener attrInterpolatorInflaterListener,AttrResourceInflaterListener attrResourceInflaterListener)
    {
        this.attrLayoutInflaterListener = attrLayoutInflaterListener;
        this.attrDrawableInflaterListener = attrDrawableInflaterListener;
        this.attrAnimationInflaterListener = attrAnimationInflaterListener;
        this.attrAnimatorInflaterListener = attrAnimatorInflaterListener;
        this.attrInterpolatorInflaterListener = attrInterpolatorInflaterListener;
        this.attrResourceInflaterListener = attrResourceInflaterListener;
    }

    public AttrLayoutInflaterListener getAttrLayoutInflaterListener()
    {
        return attrLayoutInflaterListener;
    }

    public AttrDrawableInflaterListener getAttrDrawableInflaterListener()
    {
        return attrDrawableInflaterListener;
    }

    public AttrAnimationInflaterListener getAttrAnimationInflaterListener()
    {
        return attrAnimationInflaterListener;
    }

    public AttrAnimatorInflaterListener getAttrAnimatorInflaterListener()
    {
        return attrAnimatorInflaterListener;
    }

    public AttrInterpolatorInflaterListener getAttrInterpolatorInflaterListener()
    {
        return attrInterpolatorInflaterListener;
    }

    public AttrResourceInflaterListener getAttrResourceInflaterListener()
    {
        return attrResourceInflaterListener;
    }
}
