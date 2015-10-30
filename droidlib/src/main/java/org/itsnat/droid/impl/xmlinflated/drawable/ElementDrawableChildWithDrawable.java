package org.itsnat.droid.impl.xmlinflated.drawable;

import android.graphics.drawable.Drawable;

/**
 * Created by jmarranz on 30/11/14.
 */
public abstract class ElementDrawableChildWithDrawable extends ElementDrawableChild implements ElementDrawableContainer
{
    public ElementDrawableChildWithDrawable(ElementDrawable parentElementDrawable)
    {
        super(parentElementDrawable);
    }
}
