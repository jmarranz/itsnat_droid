package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildDrawableBridge;


/**
 * Created by Jose on 15/10/2015.
 */
public class ElementDrawableChildContainerDrawableBridge extends ElementDrawableChildContainer<ElementDrawableChildDrawableBridge>
{
    public ElementDrawableChildContainerDrawableBridge(ElementDrawableChildDrawableBridge elemDrawableChild)
    {
        super(elemDrawableChild);
    }


    @Override
    public Drawable getInstanceToSetAttributes()
    {
        return ((ElementDrawableChildDrawableBridge)elemDrawableChild).getDrawable();
    }
}
