package org.itsnat.droid.impl.xmlinflated.drawable;

import android.graphics.drawable.Drawable;

/**
 * Created by jmarranz on 27/11/14.
 */
public class LevelListDrawableChildItem extends ElementDrawableChildWithDrawable
{
    /*
    <item
        android:drawable="@drawable/drawable_resource"
        android:maxLevel="integer"
        android:minLevel="integer" />
    */

    protected Integer maxLevel;
    protected Integer minLevel;

    public LevelListDrawableChildItem(ElementDrawableChildBase parentElementDrawable)
    {
        super(parentElementDrawable);
    }

    public Drawable getDrawable() // Necesita ser encontrado por AttrDescReflecMethodDrawable aunque esté en ElementDrawableChildWithDrawable
    {
        return super.getDrawable();
    }

    public void setDrawable(Drawable drawable) // Necesita ser encontrado por AttrDescReflecMethodDrawable aunque esté en ElementDrawableChildWithDrawable
    {
        super.setDrawable(drawable);
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public Integer getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

}
