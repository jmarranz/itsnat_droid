package org.itsnat.droid.impl.xmlinflated.drawable;

import java.util.ArrayList;

/**
 * Created by jmarranz on 1/12/14.
 */
public abstract class ElementDrawable
{
    protected ArrayList<ElementDrawable> childDrawableList;

    public ArrayList<ElementDrawable> getChildElementDrawableList()
    {
        return childDrawableList;
    }

    public void initChildElementDrawableList(int size)
    {
        this.childDrawableList = new ArrayList<ElementDrawable>(size);
    }

    public void addChildElementDrawable(ElementDrawable child)
    {
        childDrawableList.add(child);
    }

    public abstract ElementDrawableRoot getElementDrawableRoot();
}
