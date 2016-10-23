package org.itsnat.droid.impl.xmlinflated.drawable;

import android.graphics.drawable.Drawable;

/**
 * Created by jmarranz on 27/11/14.
 */
public class AnimationDrawableChildItem extends ElementDrawableChildWithDrawable
{
    protected Integer duration;

    public AnimationDrawableChildItem(ElementDrawableChildBase parentElementDrawable)
    {
        super(parentElementDrawable);
    }

    public Drawable getDrawable() // Necesita ser encontrado por AttrDescReflecMethodDrawable aunque esté en ElementDrawableChildWithDrawable
    {
        return super.getDrawable();
    }

    public void setDrawable(Drawable drawable) // Necesita ser encontrado por AttrDescReflecMethodDrawable aunque esté en ElementDrawableChildWithDrawable
    {
        super.setDrawable(drawable);
    }

    public Integer getDuration()
    {
        return duration;
    }

    public void setDuration(int id)
    {
        this.duration = id;
    }
}
