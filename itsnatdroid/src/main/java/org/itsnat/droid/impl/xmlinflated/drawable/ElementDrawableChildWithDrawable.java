package org.itsnat.droid.impl.xmlinflated.drawable;

import android.graphics.drawable.Drawable;

/**
 * Created by jmarranz on 30/11/14.
 */
public abstract class ElementDrawableChildWithDrawable extends ElementDrawableChild
{
    protected Drawable drawable;

    public ElementDrawableChildWithDrawable(ElementDrawableChildBase parentElementDrawable)
    {
        super(parentElementDrawable);
    }

    public Drawable getDrawable()
    {
        return drawable;
    }

    public void setDrawable(Drawable drawable)
    {
        this.drawable = drawable;
    }
}
