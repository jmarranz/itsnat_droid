package org.itsnat.droid.impl.xmlinflated.drawable;

import java.util.ArrayList;

/**
 * Created by jmarranz on 1/12/14.
 */
public abstract class ElementDrawableChildBase
{
    protected ArrayList<ElementDrawableChildBase> itemDrawableList;
    protected ElementDrawableChildBase parentElementDrawable;

    protected ElementDrawableChildBase(ElementDrawableChildBase parentElementDrawable)
    {
        this.parentElementDrawable = parentElementDrawable;
    }

    public ArrayList<ElementDrawableChildBase> getElementDrawableChildList()
    {
        return itemDrawableList;
    }

    public void initElementDrawableItemList(int size)
    {
        this.itemDrawableList = new ArrayList<ElementDrawableChildBase>(size);
    }

    public void addElementDrawableItem(ElementDrawableChildBase child)
    {
        itemDrawableList.add(child);
    }


    public ElementDrawableChildBase getParentElementDrawable()
    {
        return parentElementDrawable;
    }


    public ElementDrawableChildRoot getElementDrawableChildRoot()
    {
        return parentElementDrawable != null ?  parentElementDrawable.getElementDrawableChildRoot() : null;
    }
}
