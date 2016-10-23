package org.itsnat.droid.impl.xmlinflated.drawable;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawableBased;

/**
 * Created by jmarranz on 30/11/14.
 */
public class ElementDrawableChildDrawableBridge extends ElementDrawableChild
{
    private ClassDescElementDrawableBased<Drawable> classDescBridge;
    private Drawable drawable;

    public ElementDrawableChildDrawableBridge(ElementDrawableChildBase parentChildDrawable, ClassDescElementDrawableBased<Drawable> classDescBridge, Drawable drawable)
    {
        super(parentChildDrawable);
        this.classDescBridge = classDescBridge;
        this.drawable = drawable;

        if (parentChildDrawable instanceof ElementDrawableChildRoot)
            ((ElementDrawableChildRoot)parentChildDrawable).setDrawable(drawable);
        else if (parentChildDrawable instanceof ElementDrawableChildWithDrawable)
            ((ElementDrawableChildWithDrawable)parentChildDrawable).setDrawable(drawable);
        else
            throw MiscUtil.internalError();
    }

    public Drawable getDrawable()
    {
        return drawable;
    }

    public ClassDescElementDrawableBased<Drawable> getClassDescElementDrawableBasedBridgeRoot()
    {
        return classDescBridge;
    }

/*
    public ElementDrawableChildRoot getElementDrawableChildRoot()
    {
        return (ElementDrawableChildRoot)getParentElementDrawableChildBase();
    }

    public ClassDescElementDrawableBased getClassDescRootDrawableBridge()
    {
        return classDescBridge;
    }

    public Drawable getDrawable()
    {
        return getElementDrawableChildRoot().getDrawable();
    }
*/

/*
    public ElementDrawableChildWithDrawable getElementDrawableChildWithDrawable()
    {
        return (ElementDrawableChildWithDrawable) getParentElementDrawableChildBase();
    }

    public Drawable getDrawable()
    {
        return getElementDrawableChildWithDrawable().getDrawable();
    }
*/
}
