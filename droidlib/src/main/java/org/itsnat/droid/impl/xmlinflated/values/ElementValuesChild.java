package org.itsnat.droid.impl.xmlinflated.values;

/**
 * Created by jmarranz on 27/11/14.
 */
public abstract class ElementValuesChild extends ElementValues
{
    protected String name;
    protected ElementValues parentElement;

    protected ElementValuesChild(String elementName, String name, ElementValues parentElement)
    {
        super(elementName);
        this.name = name;
        this.parentElement = parentElement;
    }

    public String getName()
    {
        return name;
    }

    public ElementValues getParentElement()
    {
        return parentElement;
    }

    public ElementValuesResources getRootElementResources()
    {
        return parentElement.getRootElementResources();
    }
}
