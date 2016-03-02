package org.itsnat.droid.impl.xmlinflated.drawable;

/**
 * Created by jmarranz on 27/11/14.
 */
public class TransitionDrawableItem extends LayerDrawableItem
{
    /*
    Same attributes than <item> of LayerDrawable because: TransitionDrawable extends LayerDrawableItem
    <item
        android:drawable="@[package:]drawable/drawable_resource"
        android:id="@[+][package:]id/resource_name"
        android:top="dimension"
        android:right="dimension"
        android:bottom="dimension"
        android:left="dimension" />
    */

    public TransitionDrawableItem(ElementDrawable parentElementDrawable)
    {
        super(parentElementDrawable);
    }

}
