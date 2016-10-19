package org.itsnat.droid.impl.xmlinflated.drawable;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawable;

/**
 * Created by jmarranz on 30/11/14.
 */
public class ElementDrawableChildDrawableBridge extends ElementDrawableChild
{
    protected ClassDescElementDrawable classDescBridge;

    public ElementDrawableChildDrawableBridge(ElementDrawableChildBase parentChildDrawable, ClassDescElementDrawable classDescBridge, Drawable drawable)
    {
        super(parentChildDrawable);
        this.classDescBridge = classDescBridge;

        ((ElementDrawableChildWithDrawable)getParentElementDrawable()).setDrawable(drawable);
    }

    public ClassDescElementDrawable getClassDescRootDrawableBridge()
    {
        return classDescBridge;
    }

    public ElementDrawableChildWithDrawable getElementDrawableItemWithDrawable()
    {
        return (ElementDrawableChildWithDrawable)getParentElementDrawable();
    }


    public Drawable getDrawable()
    {
        return getElementDrawableItemWithDrawable().getDrawable();
    }
}
