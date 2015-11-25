package org.itsnat.droid.impl.xmlinflated.drawable;

import android.graphics.drawable.Drawable;

/**
 * Created by jmarranz on 27/11/14.
 */
public class AnimationDrawableItem extends ElementDrawableChildWithDrawable
{
    protected Drawable drawable;
    protected Integer duration;

    public AnimationDrawableItem(ElementDrawable parentElementDrawable)
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

    public Integer getDuration()
    {
        return duration;
    }

    public void setDuration(int id)
    {
        this.duration = id;
    }
}
