package org.itsnat.droid.impl.dom.values;

import org.itsnat.droid.impl.dom.DOMAttr;

import java.util.Locale;

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
    public DOMAttr setTextNode(String text, Locale locale)
    {
        this.valueAsDOMAttr = DOMAttr.create(null, getValueAsDOMAttrName(), text,locale);

        return valueAsDOMAttr;
    }
}
