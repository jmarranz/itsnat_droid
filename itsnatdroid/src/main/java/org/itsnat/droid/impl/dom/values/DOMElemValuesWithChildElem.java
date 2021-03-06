package org.itsnat.droid.impl.dom.values;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;

/**
 * Created by jmarranz on 02/02/2016.
 */
public abstract class DOMElemValuesWithChildElem extends DOMElemValues
{
    public DOMElemValuesWithChildElem(String tagName, DOMElemValues parentElement)
    {
        super(tagName, parentElement);
    }

    public String getNameAttr()
    {
        DOMAttr nameAttr = getDOMAttribute(null, "name");
        if (nameAttr == null) throw new ItsNatDroidException("Missing name attribute in <" + getTagName() + ">");
        return nameAttr.getValue();
    }
}
