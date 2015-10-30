package org.itsnat.droid.impl.xmlinflated.drawable;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawableRoot;

/**
 * Created by jmarranz on 30/11/14.
 */
public class ElementDrawableChildDrawableBridge extends ElementDrawableChild
{
    protected ClassDescElementDrawableRoot classDescBridge;

    public ElementDrawableChildDrawableBridge(ElementDrawable parentChildDrawable, ClassDescElementDrawableRoot classDescBridge, Drawable drawable)
    {
        super(parentChildDrawable);
        this.classDescBridge = classDescBridge;

        ((ElementDrawableContainer)getParentElementDrawable()).setDrawable(drawable);
    }

    public ClassDescElementDrawableRoot getClassDescRootDrawableBridge()
    {
        return classDescBridge;
    }

    public ElementDrawableContainer getElementDrawableContainer()
    {
        return (ElementDrawableContainer)getParentElementDrawable();
    }


    public Drawable getDrawable()
    {
        return getElementDrawableContainer().getDrawable();
    }
}
