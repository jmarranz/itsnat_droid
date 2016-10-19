package org.itsnat.droid.impl.xmlinflated.drawable;

/**
 * Created by jmarranz on 27/11/14.
 */
public class LayerDrawableChildItem extends ElementDrawableChildWithDrawable
{
    /*
    <item
    android:drawable="@[package:]drawable/drawable_resource"
    android:id="@[+][package:]id/resource_name"
    android:top="dimension"
    android:right="dimension"
    android:bottom="dimension"
    android:left="dimension" />
    */

    protected int id;
    protected int top;
    protected int right;
    protected int bottom;
    protected int left;

    public LayerDrawableChildItem(ElementDrawableChildBase parentElementDrawable)
    {
        super(parentElementDrawable);
    }


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getTop()
    {
        return top;
    }

    public void setTop(int top)
    {
        this.top = top;
    }

    public int getRight()
    {
        return right;
    }

    public void setRight(int right)
    {
        this.right = right;
    }

    public int getBottom()
    {
        return bottom;
    }

    public void setBottom(int bottom)
    {
        this.bottom = bottom;
    }

    public int getLeft()
    {
        return left;
    }

    public void setLeft(int left)
    {
        this.left = left;
    }
}
