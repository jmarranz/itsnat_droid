package org.itsnat.droid.impl.dom.values;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;

/**
 * Created by jmarranz on 02/02/2016.
 */
public abstract class DOMElemValuesNoChildElem extends DOMElemValues
{
    protected DOMAttr valueAsDOMAttr; // Nos interesa guardarlo el child text node hijo con atributo para poder resolver atributos dinámicos con el valor (asset y remote)

    public DOMElemValuesNoChildElem(String name, DOMElemValues parentElement)
    {
        super(name, parentElement);
    }

    public DOMAttr getValueAsDOMAttr()
    {
        return valueAsDOMAttr;
    }

    public String getItemName()
    {
        DOMAttr nameAttr = getDOMAttribute(null, "name");
        if (nameAttr == null) throw new ItsNatDroidException("Missing name attribute in item");
        return nameAttr.getValue();
    }

    public abstract DOMAttr setTextNode(String text);
}
