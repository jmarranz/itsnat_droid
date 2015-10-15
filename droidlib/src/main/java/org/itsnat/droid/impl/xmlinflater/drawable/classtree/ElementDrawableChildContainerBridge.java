package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBitmapBridge;


/**
 * Created by Jose on 15/10/2015.
 */
public class ElementDrawableChildContainerBridge extends ElementDrawableChildContainer
{
    public ElementDrawableChildContainerBridge(ElementDrawableChildBitmapBridge elemDrawableChild)
    {
        super(elemDrawableChild);
    }


    @Override
    public Object getInstanceToSetAttributes()
    {
        return ((ElementDrawableChildBitmapBridge)elemDrawableChild).getDrawable();
    }
}
