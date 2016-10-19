package org.itsnat.droid.impl.xmlinflated.drawable;

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

    public Integer getDuration()
    {
        return duration;
    }

    public void setDuration(int id)
    {
        this.duration = id;
    }
}
