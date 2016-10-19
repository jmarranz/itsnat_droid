package org.itsnat.droid.impl.xmlinflated.drawable;

/**
 * Created by jmarranz on 27/11/14.
 */
public class GradientDrawableChildStroke extends ElementDrawableChildNormal
{
    protected Integer width;
    protected Integer color;
    protected Float dashWidth;
    protected Float dashGap;

    public GradientDrawableChildStroke(ElementDrawableChildBase parentElementDrawable)
    {
        super(parentElementDrawable);
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Float getDashWidth()
    {
        return dashWidth;
    }

    public void setDashWidth(float dashWidth)
    {
        this.dashWidth = dashWidth;
    }

    public Float getDashGap()
    {
        return dashGap;
    }

    public void setDashGap(float dashGap)
    {
        this.dashGap = dashGap;
    }
}
