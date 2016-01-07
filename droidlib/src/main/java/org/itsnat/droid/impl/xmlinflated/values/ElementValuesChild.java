package org.itsnat.droid.impl.xmlinflated.values;

/**
 * Created by jmarranz on 27/11/14.
 */
public abstract class ElementValuesChild extends ElementValues
{
    protected ElementValues parentElement;

    protected ElementValuesChild(ElementValues parentElement)
    {
        this.parentElement = parentElement;
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
