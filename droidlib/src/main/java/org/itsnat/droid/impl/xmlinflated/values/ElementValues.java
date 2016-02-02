package org.itsnat.droid.impl.xmlinflated.values;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmarranz on 1/12/14.
 */
public abstract class ElementValues
{
    protected String elementName;
    protected ArrayList<ElementValues> childElemValuesList;

    public ElementValues(String elementName)
    {
        this.elementName = elementName;
    }

    public String getElementName()
    {
        return elementName;
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
