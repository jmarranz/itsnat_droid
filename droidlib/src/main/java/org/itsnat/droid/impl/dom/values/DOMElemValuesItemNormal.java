package org.itsnat.droid.impl.dom.values;

import org.itsnat.droid.impl.dom.DOMAttr;

import java.util.Locale;

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
    public DOMAttr setTextNode(String text, Locale locale)
    {
        String itemName = getNameAttr();

        this.valueAsDOMAttr = DOMAttr.create(null, itemName, text,locale);

        return valueAsDOMAttr;
    }
}
