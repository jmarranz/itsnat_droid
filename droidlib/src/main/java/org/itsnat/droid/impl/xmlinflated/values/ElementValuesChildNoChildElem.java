package org.itsnat.droid.impl.xmlinflated.values;

/**
 * Soportamos en la misma clase los dos formatos equivalentes a la hora de definir un valor cuyo tipo hay que especificar explícitamente:
 *
 * <type name="">value</type>
 * <item name="" type="">value</item>
 *
 * Created by jmarranz on 27/11/14.
 */
public class ElementValuesChildNoChildElem extends ElementValuesChild
{
    protected String type; // dimen, color etc
    protected String value;

    public ElementValuesChildNoChildElem(String elementName,ElementValuesResources parentElement, String type, String name, String value)
    {
        super(elementName,name,parentElement);

        this.type = type;
        this.value = value;
    }

    public String getType()
    {
        return type;
    }

    public String getValue()
    {
        return value;
    }

}
