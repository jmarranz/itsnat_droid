package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class DrawableOrElementDrawableWrapper<TdrawableOrElementDrawableChildNormal>
{
    public abstract Drawable getDrawable();
    public abstract Object getInstanceToSetAttributes();
}
