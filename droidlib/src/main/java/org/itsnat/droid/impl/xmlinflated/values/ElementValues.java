package org.itsnat.droid.impl.xmlinflated.values;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmarranz on 1/12/14.
 */
public abstract class ElementValues
{
    protected String tagName;
    protected ArrayList<ElementValues> childElemValuesList;

    public ElementValues(String tagName)
    {
        this.tagName = tagName;
    }

    public String getTagName()
    {
        return tagName;
    }

    public List<ElementValues> getChildElementValuesList()
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
