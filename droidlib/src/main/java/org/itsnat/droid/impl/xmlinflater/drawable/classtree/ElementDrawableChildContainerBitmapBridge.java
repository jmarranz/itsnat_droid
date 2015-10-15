package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBitmapBridge;


/**
 * Created by Jose on 15/10/2015.
 */
public class ElementDrawableChildContainerBitmapBridge extends ElementDrawableChildContainer<ElementDrawableChildBitmapBridge>
{
    public ElementDrawableChildContainerBitmapBridge(ElementDrawableChildBitmapBridge elemDrawableChild)
    {
        super(elemDrawableChild);
    }


    @Override
    public Drawable getInstanceToSetAttributes()
    {
        return ((ElementDrawableChildBitmapBridge)elemDrawableChild).getDrawable();
    }
}
