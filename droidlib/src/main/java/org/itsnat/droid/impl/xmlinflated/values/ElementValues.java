package org.itsnat.droid.impl.xmlinflated.values;

import java.util.ArrayList;

/**
 * Created by jmarranz on 1/12/14.
 */
public abstract class ElementValues
{
    protected ArrayList<ElementValues> childElemValuesList;

    public ArrayList<ElementValues> getChildElementDrawableList()
    {
        return childElemValuesList;
    }

    public void initChildElementValuesList(int size)
    {
        this.childElemValuesList = new ArrayList<ElementValues>(size);
    }

    public void addChildElementValues(ElementValues child)
    {
        childElemValuesList.add(child);
    }

    public abstract ElementValuesResources getRootElementResources();
}
