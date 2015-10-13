package org.itsnat.droid.impl.xmlinflated.drawable;

import android.graphics.drawable.Drawable;

/**
 * Created by jmarranz on 27/11/14.
 */
public class StateListDrawableItem extends ElementDrawableChild implements ElementDrawableContainer
{
    /*
    <item
        android:drawable="@[package:]drawable/drawable_resource"
        android:state_pressed=["true" | "false"]
        android:state_focused=["true" | "false"]
        android:state_hovered=["true" | "false"]
        android:state_selected=["true" | "false"]
        android:state_checkable=["true" | "false"]
        android:state_checked=["true" | "false"]
        android:state_enabled=["true" | "false"]
        android:state_activated=["true" | "false"]
        android:state_window_focused=["true" | "false"] />
    */

    protected Drawable drawable;

    protected Boolean state_pressed;
    protected Boolean state_focused;
    protected Boolean state_hovered;
    protected Boolean state_selected;
    protected Boolean state_checkable;
    protected Boolean state_checked;
    protected Boolean state_enabled;
    protected Boolean state_activated;
    protected Boolean state_window_focused;

    protected int definedCount = 0;

    public StateListDrawableItem()
    {
    }

    public Drawable getDrawable()
    {
        return drawable;
    }

    public void setDrawable(Drawable drawable)
    {
        this.drawable = drawable;
    }

    public int getDefinedCount()
    {
        return definedCount;
    }

    public Boolean getState_pressed() {
        return state_pressed;
    }

    public void setState_pressed(boolean state_pressed) {
        this.state_pressed = Boolean.valueOf(state_pressed);
        definedCount++;
    }

    public Boolean getState_focused() {
        return state_focused;
    }

    public void setState_focused(boolean state_focused) {
        this.state_focused = state_focused;
        definedCount++;
    }


}
