package org.itsnat.droid.impl.xmlinflated.drawable;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by jmarranz on 27/11/14.
 */
public class ElementDrawableRoot extends ElementDrawable implements ElementDrawableContainer
{
    protected Drawable drawable;

    public ElementDrawableRoot(Drawable drawable)
    {
        this.drawable = drawable;
    }

    public ElementDrawableRoot()
    {
    }

/*
    public void setChildElementDrawableList(ArrayList<ElementDrawable> childDrawableList)
    {
        this.childDrawableList = childDrawableList;
    }
*/

    public Drawable getDrawable()
    {
        return drawable;
    }

    public void setDrawable(Drawable drawable)
    {
        this.drawable = drawable;
    }

    public ElementDrawableRoot getElementDrawableRoot()
    {
        return this;
    }
}
