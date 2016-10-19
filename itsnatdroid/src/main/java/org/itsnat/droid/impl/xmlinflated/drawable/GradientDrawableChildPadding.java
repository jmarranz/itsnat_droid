package org.itsnat.droid.impl.xmlinflated.drawable;

/**
 * Created by jmarranz on 27/11/14.
 */
public class GradientDrawableChildPadding extends ElementDrawableChildNormal
{
    protected Integer left;
    protected Integer top;
    protected Integer right;
    protected Integer bottom;

    public GradientDrawableChildPadding(ElementDrawableChildBase parentElementDrawable)
    {
        super(parentElementDrawable);
    }

    public Integer getLeft()
    {
        return left;
    }

    public void setLeft(int left)
    {
        this.left = left;
    }

    public Integer getTop()
    {
        return top;
    }

    public void setTop(int top)
    {
        this.top = top;
    }

    public Integer getRight()
    {
        return right;
    }

    public void setRight(int right)
    {
        this.right = right;
    }

    public Integer getBottom()
    {
        return bottom;
    }

    public void setBottom(int bottom)
    {
        this.bottom = bottom;
    }
}
