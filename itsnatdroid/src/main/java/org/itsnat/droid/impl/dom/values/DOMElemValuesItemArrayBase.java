package org.itsnat.droid.impl.dom.values;

import org.itsnat.droid.impl.dom.DOMAttr;

/**
 * Created by jmarranz on 02/02/2016.
 */
public class DOMElemValuesItemArrayBase extends DOMElemValuesNoChildElem
{
    public DOMElemValuesItemArrayBase(DOMElemValuesArrayBase parentElement)
    {
        super("item", parentElement);
    }

    protected String getValueAsDOMAttrName()
    {
        return parentElement.getTagName() + "-item";
    }

    @Override
    public DOMAttr setTextNode(String text)
    {
        this.valueAsDOMAttr = DOMAttr.createDOMAttr(null, getValueAsDOMAttrName(), text);

        return valueAsDOMAttr;
    }
}
