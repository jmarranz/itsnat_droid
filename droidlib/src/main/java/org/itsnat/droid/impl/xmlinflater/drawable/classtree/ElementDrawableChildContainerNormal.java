package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildNormal;

/**
 * Created by Jose on 15/10/2015.
 */
public class ElementDrawableChildContainerNormal extends ElementDrawableChildContainer<ElementDrawableChildNormal>
{
    public ElementDrawableChildContainerNormal(ElementDrawableChildNormal elemDrawableChild)
    {
        super(elemDrawableChild);
    }


    @Override
    public Object getInstanceToSetAttributes()
    {
        return (ElementDrawableChildNormal)elemDrawableChild;
    }
}
