package org.itsnat.droid.impl.xmlinflater;

import org.itsnat.droid.AttrAnimationInflaterListener;
import org.itsnat.droid.AttrAnimatorInflaterListener;
import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;

/**
 * Created by jmarranz on 22/03/2016.
 */
public class AttrInflaterListeners
{
    private final AttrLayoutInflaterListener attrLayoutInflaterListener;
    private final AttrDrawableInflaterListener attrDrawableInflaterListener;
    private final AttrAnimationInflaterListener attrAnimationInflaterListener;
    private final AttrAnimatorInflaterListener attrAnimatorInflaterListener;

    public AttrInflaterListeners(AttrLayoutInflaterListener attrLayoutInflaterListener, AttrDrawableInflaterListener attrDrawableInflaterListener,
                                 AttrAnimationInflaterListener attrAnimationInflaterListener, AttrAnimatorInflaterListener attrAnimatorInflaterListener)
    {
        this.attrLayoutInflaterListener = attrLayoutInflaterListener;
        this.attrDrawableInflaterListener = attrDrawableInflaterListener;
        this.attrAnimationInflaterListener = attrAnimationInflaterListener;
        this.attrAnimatorInflaterListener = attrAnimatorInflaterListener;
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
}
