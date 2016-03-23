package org.itsnat.droid.impl.xmlinflater;

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
    private final AttrAnimatorInflaterListener attrAnimatorInflaterListener;

    public AttrInflaterListeners(AttrLayoutInflaterListener attrLayoutInflaterListener, AttrDrawableInflaterListener attrDrawableInflaterListener, AttrAnimatorInflaterListener attrAnimatorInflaterListener)
    {
        this.attrLayoutInflaterListener = attrLayoutInflaterListener;
        this.attrDrawableInflaterListener = attrDrawableInflaterListener;
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

    public AttrAnimatorInflaterListener getAttrAnimatorInflaterListener()
    {
        return attrAnimatorInflaterListener;
    }
}
