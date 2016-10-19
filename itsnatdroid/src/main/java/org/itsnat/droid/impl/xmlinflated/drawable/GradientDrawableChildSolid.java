package org.itsnat.droid.impl.xmlinflated.drawable;

/**
 * Created by jmarranz on 27/11/14.
 */
public class GradientDrawableChildSolid extends ElementDrawableChildNormal
{
    protected Integer color;

    public GradientDrawableChildSolid(ElementDrawableChildBase parentElementDrawable)
    {
        super(parentElementDrawable);
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
