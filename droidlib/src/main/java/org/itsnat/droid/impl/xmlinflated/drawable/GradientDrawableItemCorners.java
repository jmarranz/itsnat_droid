package org.itsnat.droid.impl.xmlinflated.drawable;

/**
 * Created by jmarranz on 27/11/14.
 */
public class GradientDrawableItemCorners extends ElementDrawableChildNormal
{
    protected Integer radius;
    protected Integer topLeftRadius;
    protected Integer topRightRadius;
    protected Integer bottomLeftRadius;
    protected Integer bottomRightRadius;

    public GradientDrawableItemCorners(ElementDrawable parentElementDrawable)
    {
        super(parentElementDrawable);
    }

    public Integer getRadius()
    {
        return radius;
    }

    public void setRadius(int radius)
    {
        this.radius = radius;
    }

    public Integer getTopLeftRadius()
    {
        return topLeftRadius;
    }

    public void setTopLeftRadius(int topLeftRadius)
    {
        this.topLeftRadius = topLeftRadius;
    }

    public Integer getTopRightRadius()
    {
        return topRightRadius;
    }

    public void setTopRightRadius(int topRightRadius)
    {
        this.topRightRadius = topRightRadius;
    }

    public Integer getBottomLeftRadius()
    {
        return bottomLeftRadius;
    }

    public void setBottomLeftRadius(int bottomLeftRadius)
    {
        this.bottomLeftRadius = bottomLeftRadius;
    }

    public Integer getBottomRightRadius()
    {
        return bottomRightRadius;
    }

    public void setBottomRightRadius(int bottomRightRadius)
    {
        this.bottomRightRadius = bottomRightRadius;
    }
}
