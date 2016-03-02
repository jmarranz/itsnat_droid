package org.itsnat.droid.impl.xmlinflated.values;

import org.itsnat.droid.impl.dom.DOMAttr;

/**
 *
 * Created by jmarranz on 27/11/14.
 */
public class ElementValuesStyle extends ElementValuesChildWithChildElem
{
    protected DOMAttr parentAttr; // Puede ser null (no hay atributo parent="...")

    public ElementValuesStyle(String name, DOMAttr parentAttr, ElementValuesResources parentElement)
    {
        super("style", name, parentElement);
        this.parentAttr = parentAttr;
    }

    public DOMAttr getParentAttr()
    {
        return parentAttr;
    }
}
