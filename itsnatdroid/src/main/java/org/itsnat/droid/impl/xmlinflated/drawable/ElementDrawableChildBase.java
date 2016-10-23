package org.itsnat.droid.impl.xmlinflated.drawable;

import java.util.ArrayList;

/**
 * Created by jmarranz on 1/12/14.
 */
public abstract class ElementDrawableChildBase
{
    protected ArrayList<ElementDrawableChildBase> elementDrawableChildList;
    protected ElementDrawableChildBase parentElementDrawableChild;

    protected ElementDrawableChildBase(ElementDrawableChildBase parentElementDrawableChild)
    {
        this.parentElementDrawableChild = parentElementDrawableChild;
    }

    public ArrayList<ElementDrawableChildBase> getElementDrawableChildList()
    {
        return elementDrawableChildList;
    }

    public void initElementDrawableChildList(int size)
    {
        this.elementDrawableChildList = new ArrayList<ElementDrawableChildBase>(size);
    }

    public void addElementDrawableChild(ElementDrawableChildBase child)
    {
        elementDrawableChildList.add(child);
    }


    public ElementDrawableChildBase getParentElementDrawableChildBase()
    {
        return parentElementDrawableChild;
    }


    public ElementDrawableChildRoot getElementDrawableChildRoot()
    {
        return parentElementDrawableChild != null ?  parentElementDrawableChild.getElementDrawableChildRoot() : null;
    }
}
