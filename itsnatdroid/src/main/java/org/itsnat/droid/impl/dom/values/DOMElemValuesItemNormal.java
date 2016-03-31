package org.itsnat.droid.impl.dom.values;

import org.itsnat.droid.impl.dom.DOMAttr;

/**
 * Created by jmarranz on 02/02/2016.
 */
public class DOMElemValuesItemNormal extends DOMElemValuesItemNamed
{
    public DOMElemValuesItemNormal(String tagName, DOMElemValuesResources parentElement)
    {
        super(tagName, parentElement);
    }

    @Override
    public DOMAttr setTextNode(String text)
    {
        String itemName = getNameAttr();

        this.valueAsDOMAttr = DOMAttr.createDOMAttr(null, itemName, text);

        return valueAsDOMAttr;
    }
}
