package org.itsnat.droid.impl.xmlinflated.drawable;

import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmarranz on 27/11/14.
 */
public class StateListDrawableItem extends ElementDrawableChildWithDrawable
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

    protected Boolean constantSize;
    protected Drawable drawable;

    protected Map<Integer,Boolean> stateMap = new HashMap<Integer,Boolean>();

    protected Boolean variablePadding;
    protected Boolean visible;

    public StateListDrawableItem(ElementDrawable parentElementDrawable)
    {
        super(parentElementDrawable);
    }


    public Drawable getDrawable()
    {
        return drawable;
    }

    public void setDrawable(Drawable drawable)
    {
        this.drawable = drawable;
    }

    public Map<Integer,Boolean> getStateMap()
    {
        return stateMap;
    }

    public Boolean getConstantSize() {
        return constantSize;
    }

    public void setConstantSize(boolean constantSize) {
        this.constantSize = constantSize;
    }

    // Ver la lista de states contemplados en ClassDescStateListDrawableItem

    public void setState_pressed(boolean state_pressed) {
        stateMap.put(android.R.attr.state_pressed,state_pressed);
    }

    public void setState_focused(boolean state_focused) {
        stateMap.put(android.R.attr.state_focused,state_focused);
    }

    public void setState_hovered(boolean state_hovered) {
        stateMap.put(android.R.attr.state_hovered,state_hovered);
    }

    public void setState_selected(boolean state_selected) {
        stateMap.put(android.R.attr.state_selected,state_selected);
    }

    public void setState_checkable(boolean state_checkable) {
        stateMap.put(android.R.attr.state_checkable,state_checkable);
    }

    public void setState_checked(boolean state_checked) {
        stateMap.put(android.R.attr.state_checked,state_checked);
    }

    public void setState_enabled(boolean state_enabled) {
        stateMap.put(android.R.attr.state_enabled,state_enabled);
    }

    public void setState_activated(boolean state_activated) {
        stateMap.put(android.R.attr.state_activated,state_activated);
    }

    public void setState_window_focused(boolean state_window_focused) {
        stateMap.put(android.R.attr.state_window_focused,state_window_focused);
    }

    public void setState_active(boolean state_active) {
        stateMap.put(android.R.attr.state_active,state_active);
    }

    public void setState_single(boolean state_single) {
        stateMap.put(android.R.attr.state_single,state_single);
    }

    public void setState_first(boolean state_first) {
        stateMap.put(android.R.attr.state_first,state_first);
    }

    public void setState_middle(boolean state_middle) {
        stateMap.put(android.R.attr.state_middle,state_middle);
    }

    public void setState_last(boolean state_last) {
        stateMap.put(android.R.attr.state_last,state_last);
    }

    public Boolean getVariablePadding() {
        return variablePadding;
    }

    public void setVariablePadding(boolean variablePadding) {
        this.variablePadding = variablePadding;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    // En teoría hay más android.R.attr.state_* en level 15 pero no tenemos ni idea de como funcionan
}
