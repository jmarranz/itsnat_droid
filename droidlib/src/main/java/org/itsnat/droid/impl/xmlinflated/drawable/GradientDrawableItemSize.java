package org.itsnat.droid.impl.xmlinflated.drawable;

/**
 * Created by jmarranz on 27/11/14.
 */
public class GradientDrawableItemSize extends ElementDrawableChildNormal
{
    protected Integer width;
    protected Integer height;

    public GradientDrawableItemSize(ElementDrawable parentElementDrawable)
    {
        super(parentElementDrawable);
    }

    public Integer getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public Integer getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }
}
