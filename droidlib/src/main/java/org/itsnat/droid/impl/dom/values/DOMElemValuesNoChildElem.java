package org.itsnat.droid.impl.dom.values;

import org.itsnat.droid.impl.dom.DOMAttr;

import java.util.Locale;

/**
 * Created by jmarranz on 02/02/2016.
 */
public abstract class DOMElemValuesNoChildElem extends DOMElemValues
{
    protected DOMAttr valueAsDOMAttr; // Nos interesa guardarlo el child text node hijo con atributo para poder resolver atributos dinámicos con el valor (asset y remote)

    public DOMElemValuesNoChildElem(String tagName, DOMElemValues parentElement)
    {
        super(tagName, parentElement);
    }

    public DOMAttr getValueAsDOMAttr()
    {
        return valueAsDOMAttr;
    }

    public abstract DOMAttr setTextNode(String text, Locale locale);
}
