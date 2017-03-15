package org.itsnat.droid.impl.xmlinflated.menu;

import java.util.ArrayList;

/**
 * Created by jmarranz on 1/12/14.
 */
public abstract class ElementMenuChildBase
{
    protected ArrayList<ElementMenuChildBase> elementMenuChildList;
    protected ElementMenuChildBase parentElementMenuChild;

    protected ElementMenuChildBase(ElementMenuChildBase parentElementMenuChild)
    {
        this.parentElementMenuChild = parentElementMenuChild;
    }

    public ArrayList<ElementMenuChildBase> getElementMenuChildList()
    {
        return elementMenuChildList;
    }

    public void initElementMenuChildList(int size)
    {
        this.elementMenuChildList = new ArrayList<ElementMenuChildBase>(size);
    }

    public void addElementMenuChild(ElementMenuChildBase child)
    {
        elementMenuChildList.add(child);
    }


    public ElementMenuChildBase getParentElementMenuChildBase()
    {
        return parentElementMenuChild;
    }


    public ElementMenuChildRoot getElementMenuChildRoot()
    {
        return parentElementMenuChild != null ?  parentElementMenuChild.getElementMenuChildRoot() : null;
    }
}
