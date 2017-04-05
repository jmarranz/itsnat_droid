package org.itsnat.droid.impl.xmlinflated.menu;

import android.view.Menu;

import java.util.ArrayList;

/**
 * Created by jmarranz on 1/12/14.
 */
public abstract class ElementMenuChildBased
{
    protected ArrayList<ElementMenuChildBased> elementMenuChildList;
    protected ElementMenuChildBased parentElementMenuChild;

    protected ElementMenuChildBased(ElementMenuChildBased parentElementMenuChild)
    {
        this.parentElementMenuChild = parentElementMenuChild;
    }

    public ArrayList<ElementMenuChildBased> getElementMenuChildList()
    {
        return elementMenuChildList;
    }

    public void initElementMenuChildList(int size)
    {
        this.elementMenuChildList = new ArrayList<ElementMenuChildBased>(size);
    }

    public void addElementMenuChild(ElementMenuChildBased child)
    {
        elementMenuChildList.add(child);
    }


    public ElementMenuChildBased getParentElementMenuChildBase()
    {
        return parentElementMenuChild;
    }


    public ElementMenuChildRoot getElementMenuChildRoot()
    {
        return parentElementMenuChild != null ?  parentElementMenuChild.getElementMenuChildRoot() : null;
    }


}
