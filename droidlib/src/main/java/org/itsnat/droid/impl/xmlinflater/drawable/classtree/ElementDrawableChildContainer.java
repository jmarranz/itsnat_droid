package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBitmapBridge;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildNormal;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ElementDrawableChildContainer extends DrawableOrElementDrawableContainer
{
    protected ElementDrawableChild elemDrawableChild;

    protected ElementDrawableChildContainer(ElementDrawableChild elemDrawableChild)
    {
        this.elemDrawableChild = elemDrawableChild;
    }

    public static ElementDrawableChildContainer create(ElementDrawableChild elemDrawableChild)
    {
        if (elemDrawableChild instanceof ElementDrawableChildNormal)
            return new ElementDrawableChildContainerNormal((ElementDrawableChildNormal)elemDrawableChild);
        else if (elemDrawableChild instanceof ElementDrawableChildBitmapBridge)
            return new ElementDrawableChildContainerBridge((ElementDrawableChildBitmapBridge)elemDrawableChild);
        else
            return null; // Never happens
    }

    public ElementDrawableChild getElementDrawableChild()
    {
        return elemDrawableChild;
    }

    @Override
    public Drawable getDrawable()
    {
        return elemDrawableChild.getElementDrawableRoot().getDrawable(); // Puede ser null si los elementos hijos se están procesando antes de crear el Drawable porque se necesitan para el constructor
    }

}
