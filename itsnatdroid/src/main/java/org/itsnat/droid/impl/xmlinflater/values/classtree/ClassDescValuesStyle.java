package org.itsnat.droid.impl.xmlinflater.values.classtree;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.values.DOMElemValuesItemStyle;
import org.itsnat.droid.impl.dom.values.DOMElemValuesStyle;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesItemStyle;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesResources;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesStyle;
import org.itsnat.droid.impl.xmlinflater.values.ClassDescValuesMgr;

import java.util.LinkedList;

/**
 * Created by jmarranz on 07/01/2016.
 */
public class ClassDescValuesStyle extends ClassDescValues<ElementValuesStyle>
{
    public ClassDescValuesStyle(ClassDescValuesMgr classMgr)
    {
        super(classMgr, "style", null);
    }

    public ElementValuesStyle createElementValuesStyle(DOMElemValuesStyle domElement, ElementValuesResources parentChildValues)
    {
        DOMAttr attrName = domElement.getDOMAttribute(null, "name");
        if (attrName == null)
            throw new ItsNatDroidException("Missing attribute name in <style>");
        String name = attrName.getValue();

        DOMAttr attrParent = domElement.getDOMAttribute(null, "parent");  // Puede ser null (no definido)
        ElementValuesStyle elementValuesStyle = new ElementValuesStyle(name,attrParent,parentChildValues);

        processChildElementValuesItemStyle(domElement, elementValuesStyle);

        return elementValuesStyle;
    }

    private void processChildElementValuesItemStyle(DOMElemValuesStyle domElemParent, ElementValuesStyle parentChildValues)
    {
        LinkedList<DOMElement> childDOMElemList = domElemParent.getChildDOMElementList();
        if (childDOMElemList == null) return;

        parentChildValues.initChildElementValuesList(childDOMElemList.size());
        for (DOMElement childDOMElem : childDOMElemList)
        {
            ElementValuesItemStyle childElemValues = inflateNextElement((DOMElemValuesItemStyle)childDOMElem,parentChildValues);
            parentChildValues.addChildElementValues(childElemValues);
        }
    }

    private ElementValuesItemStyle inflateNextElement(DOMElemValuesItemStyle domElement,ElementValuesStyle parentChildValues)
    {
        DOMAttr attrName = domElement.getDOMAttribute(null, "name");
        if (attrName == null)
            throw new ItsNatDroidException("Missing attribute name in <item>");
        String name = attrName.getValue();
        DOMAttr valueAsDOMAttr = domElement.getValueAsDOMAttr();
        return new ElementValuesItemStyle(parentChildValues,name,valueAsDOMAttr);
    }
}
