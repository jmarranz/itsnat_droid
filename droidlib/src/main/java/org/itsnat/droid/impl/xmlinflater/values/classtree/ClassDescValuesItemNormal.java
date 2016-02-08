package org.itsnat.droid.impl.xmlinflater.values.classtree;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.values.DOMElemValues;
import org.itsnat.droid.impl.dom.values.DOMElemValuesItemNormal;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesItemNormal;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesResources;
import org.itsnat.droid.impl.xmlinflater.values.ClassDescValuesMgr;

/**
 * Created by jmarranz on 07/01/2016.
 */
public class ClassDescValuesItemNormal extends ClassDescValues<ElementValuesItemNormal>
{
    public ClassDescValuesItemNormal(ClassDescValuesMgr classMgr, String elemName)
    {
        super(classMgr, elemName, null);
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

        return new ElementValuesItemNormal(domElement.getName(),parentChildValues,getType(),name, valueAsDOMAttr);
    }

    public static String getResourceType(DOMElemValues domElement)
    {
        // Sólo vale para los elementos directamente por debajo de <resources> no para los hijos de <style> cuyos <item> no tienen atributo "type"
        String elemName = domElement.getName();
        if (elemName.equals("item"))
        {
            DOMAttr attrType = domElement.getDOMAttribute(null, "type");
            if (attrType == null)
            {
                DOMAttr attrName = domElement.getDOMAttribute(null, "name");
                if (attrName == null)
                    throw new ItsNatDroidException("Missing attribute name in <item>");

                throw new ItsNatDroidException("Missing attribute type in <item> with name: " + attrName.getValue());
            }
            return attrType.getValue();
        }
        else
        {
            return elemName; // Ej <dimen> <color> etc
        }
    }
}
