package org.itsnat.droid.impl.xmlinflater.values.classtree;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.values.DOMElemValues;
import org.itsnat.droid.impl.dom.values.DOMElemValuesItemNormal;
import org.itsnat.droid.impl.dom.values.XMLDOMValues;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesItemNormal;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesResources;
import org.itsnat.droid.impl.xmlinflater.values.ClassDescValuesMgr;

import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_BOOL;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_DIMEN;

/**
 * Created by jmarranz on 07/01/2016.
 */
public class ClassDescValuesItemNormal extends ClassDescValues<ElementValuesItemNormal>
{
    public ClassDescValuesItemNormal(ClassDescValuesMgr classMgr, String tagName)
    {
        super(classMgr, tagName, null);
    }

    public String getType()
    {
        return classOrDOMElemName;
    }

    public ElementValuesItemNormal createElementValuesItemNormal(DOMElemValuesItemNormal domElement, ElementValuesResources parentChildValues)
    {
        // Se ha chequeado antes que está t_odo bien en domElement (existe type, name y value de una de las dos formas)
        DOMAttr attrName = domElement.getDOMAttribute(null, "name");
        if (attrName == null) throw MiscUtil.internalError();

        String name = attrName.getValue();
        DOMAttr valueAsDOMAttr = domElement.getValueAsDOMAttr();

        return new ElementValuesItemNormal(domElement.getTagName(),parentChildValues,getType(),name, valueAsDOMAttr);
    }

    public static String getResourceTypeItemNormal(DOMElemValues domElement)
    {
        // Sólo vale para los elementos directamente por debajo de <resources> no para los hijos de <style> cuyos <item> no tienen atributo "type"
        String tagName = domElement.getTagName();
        if (tagName.equals("item"))
        {
            DOMAttr attrType = domElement.getDOMAttribute(null, "type");
            if (attrType == null) throw new ItsNatDroidException("Missing the type attribute in <item> declaration");
            String type = attrType.getValue();
            if (XMLDOMValues.TYPE_DIMEN.equals(type))
            {
                // Vemos si hay format="..." por ejemplo format="float" (ej <item name=".." type="dimen" format="float">...</item>) reemplazaría como nombre de dimensión a type "dimen"
                // Valores esperados de format="..." (no los soportamos todos): boolean,dimension,color,enum,integer,flag,float,fraction,reference,string
                DOMAttr attrFormat = domElement.getDOMAttribute(null, "format");
                if (attrFormat != null)
                {
                    type = attrFormat.getValue();
                    if ("dimension".equals(type)) type = TYPE_DIMEN; // Usamos "dimen" que es el nombre que usamos initernamente
                    else if ("boolean".equals(type)) type = TYPE_BOOL; // Idem "bool"
                    // Los demás coinciden con el atributo type o no los usamos
                }
            }

            return type;
        }
        else
        {
            return tagName; // Ej <dimen> <color> etc
        }
    }
}
