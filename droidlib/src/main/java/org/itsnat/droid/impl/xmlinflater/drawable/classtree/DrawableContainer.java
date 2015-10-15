package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;

/**
 * Created by Jose on 15/10/2015.
 */
public class DrawableContainer extends DrawableOrElementDrawableContainer
{
    protected Drawable drawable;

    public DrawableContainer(Drawable drawable)
    {
        this.drawable = drawable;
    }

    @Override
    public Drawable getDrawable() {
        return drawable;
    }

    @Override
    public Object getInstanceToSetAttributes()
    {
        return getDrawable();
    }
}
