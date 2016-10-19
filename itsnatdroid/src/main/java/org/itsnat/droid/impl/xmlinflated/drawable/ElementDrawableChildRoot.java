package org.itsnat.droid.impl.xmlinflated.drawable;

import android.graphics.drawable.Drawable;

/**
 * Created by jmarranz on 27/11/14.
 */
public class ElementDrawableChildRoot extends ElementDrawableChild
{
    protected Drawable drawable;

    public ElementDrawableChildRoot()
    {
        super(null);
    }

    public ElementDrawableChildRoot(Drawable drawable)
    {
        super(null);
        this.drawable = drawable;
    }

    public Drawable getDrawable()
    {
        return drawable;
    }

    public void setDrawable(Drawable drawable)
    {
        this.drawable = drawable;
    }

}
