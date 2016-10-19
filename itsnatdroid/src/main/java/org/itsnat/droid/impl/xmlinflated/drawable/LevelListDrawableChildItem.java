package org.itsnat.droid.impl.xmlinflated.drawable;

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
