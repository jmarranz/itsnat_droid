package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildWithDrawable;

/**
 * Created by Jose on 15/10/2015.
 */
public class ElementDrawableChildContainerWithDrawable extends ElementDrawableChildContainer<ElementDrawableChildWithDrawable>
{
    public ElementDrawableChildContainerWithDrawable(ElementDrawableChildWithDrawable elemDrawableChild)
    {
        super(elemDrawableChild);
    }


    @Override
    public Object getInstanceToSetAttributes()
    {
        return (ElementDrawableChildWithDrawable)elemDrawableChild;
    }
}
