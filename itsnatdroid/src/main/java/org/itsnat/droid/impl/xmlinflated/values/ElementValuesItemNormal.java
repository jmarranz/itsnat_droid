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
public class ElementValuesItemNormal extends ElementValuesChildNoChildElem
{
    protected String type; // dimen, color etc

    public ElementValuesItemNormal(String tagName, ElementValuesResources parentElement, String type, String name, DOMAttr valueAsDOMAttr)
    {
        super(tagName,parentElement,name,valueAsDOMAttr);

        this.type = type;
    }

    public String getType()
    {
        return type;
    }

}
