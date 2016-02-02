package org.itsnat.droid.impl.dom.values;

import org.itsnat.droid.impl.dom.DOMAttr;

/**
 * Created by jmarranz on 02/02/2016.
 */
public class DOMElemValuesItemNormal extends DOMElemValuesNoChildElem
{
    public DOMElemValuesItemNormal(String name, DOMElemValuesResources parentElement)
    {
        super(name, parentElement);
    }

    @Override
    public DOMAttr setTextNode(String text)
    {
        String itemName = getItemName();

        this.valueAsDOMAttr = DOMAttr.create(null, itemName, text);

        return valueAsDOMAttr;
    }
}
