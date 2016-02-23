package org.itsnat.droid.impl.dom.values;

import android.content.res.Configuration;

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
    public DOMAttr setTextNode(String text,Configuration configuration)
    {
        String itemName = getNameAttr();

        this.valueAsDOMAttr = DOMAttr.create(null, itemName, text, configuration);

        return valueAsDOMAttr;
    }
}
