package org.itsnat.droid.impl.xmlinflated.values;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.InflatedXML;

/**
 * Soportamos en la misma clase los dos formatos equivalentes a la hora de definir un valor cuyo tipo hay que especificar explícitamente:
 *
 * <type name="">value</type>
 * <item name="" type="">value</item>
 *
 * Created by jmarranz on 27/11/14.
 */
public class ElementValuesChildStyleChild extends ElementValuesChild
{
    protected DOMAttr attr; // Útil si es un item de un <style>

    public ElementValuesChildStyleChild(ElementValuesChildStyle parentElement,String name, String value)
    {
        super("item", name, parentElement);

        String prefix = NamespaceUtil.getPrefix(name);
        String namespaceURI = prefix != null && prefix.equals("android") ? NamespaceUtil.XMLNS_ANDROID : null;
        if (namespaceURI != null)
            name = NamespaceUtil.getLocalName(name);

        this.attr = DOMAttr.create(namespaceURI,name,value);
    }

    public DOMAttr getDOMAttr()
    {
        return attr;
    }

}
