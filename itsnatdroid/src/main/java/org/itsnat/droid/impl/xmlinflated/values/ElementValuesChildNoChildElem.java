package org.itsnat.droid.impl.xmlinflated.values;

import org.itsnat.droid.impl.dom.DOMAttr;

/**
 * Soportamos en la misma clase los dos formatos equivalentes a la hora de definir un valor cuyo tipo hay que especificar explícitamente:
 *
 * <type name="">value</type>
 * <item name="" type="">value</item>
 *
 * Created by jmarranz on 27/11/14.
 */
public abstract class ElementValuesChildNoChildElem extends ElementValuesChild
{
    protected DOMAttr valueAsDOMAttr;

    public ElementValuesChildNoChildElem(String tagName,ElementValues parentElement, String name, DOMAttr valueAsDOMAttr)
    {
        super(tagName,name,parentElement);

        this.valueAsDOMAttr = valueAsDOMAttr;
    }

    public DOMAttr getValueAsDOMAttr()
    {
        return valueAsDOMAttr;
    }

}
