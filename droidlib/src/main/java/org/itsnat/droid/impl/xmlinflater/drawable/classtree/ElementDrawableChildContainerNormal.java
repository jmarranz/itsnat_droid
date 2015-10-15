package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildNormal;

/**
 * Created by Jose on 15/10/2015.
 */
public class ElementDrawableChildContainerNormal extends ElementDrawableChildContainer
{
    public ElementDrawableChildContainerNormal(ElementDrawableChildNormal elemDrawableChild)
    {
        super(elemDrawableChild);
    }


    @Override
    public Object getInstanceToSetAttributes()
    {
        return elemDrawableChild;
    }
}
