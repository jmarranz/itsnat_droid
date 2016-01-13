package org.itsnat.droid.impl.xmlinflater.values.classtree;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.values.DOMElemValues;
import org.itsnat.droid.impl.xmlinflated.values.ElementValues;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesChildNoChildElem;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesChildStyle;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesChildStyleChild;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesResources;
import org.itsnat.droid.impl.xmlinflater.values.ClassDescValuesMgr;
import org.itsnat.droid.impl.xmlinflater.values.XMLInflaterValues;

import java.util.LinkedList;

/**
 * Created by jmarranz on 07/01/2016.
 */
public class ClassDescValuesChildStyle extends ClassDescValues<ElementValuesChildStyle>
{
    public ClassDescValuesChildStyle(ClassDescValuesMgr classMgr)
    {
        super(classMgr, "style", null);
    }

    public ElementValuesChildStyle createElementValuesChildStyle(DOMElemValues domElement, ElementValuesResources parentChildValues)
    {
        DOMAttr attrName = domElement.findDOMAttribute(null, "name");
        if (attrName == null)
            throw new ItsNatDroidException("Missing attribute name in <style>");
        String name = attrName.getValue();
        ElementValuesChildStyle elementValuesChildStyle = new ElementValuesChildStyle(name,parentChildValues);

        processChildStyleChildElements(domElement, elementValuesChildStyle);

        return elementValuesChildStyle;
    }

    private void processChildStyleChildElements(DOMElemValues domElemParent, ElementValuesChildStyle parentChildValues)
    {
        LinkedList<DOMElement> childDOMElemList = domElemParent.getChildDOMElementList();
        if (childDOMElemList == null) return;

        parentChildValues.initChildElementValuesList(childDOMElemList.size());
        for (DOMElement childDOMElem : childDOMElemList)
        {
            ElementValuesChildStyleChild childElemValues = inflateNextElement((DOMElemValues)childDOMElem,parentChildValues);
            parentChildValues.addChildElementValues(childElemValues);
        }
    }

    private ElementValuesChildStyleChild inflateNextElement(DOMElemValues domElement,ElementValuesChildStyle parentChildValues)
    {
        DOMAttr attrName = domElement.findDOMAttribute(null, "name");
        if (attrName == null)
            throw new ItsNatDroidException("Missing attribute name in <item>");
        String name = attrName.getValue();
        String value = domElement.getText();
        return new ElementValuesChildStyleChild(parentChildValues,name,value);
    }
}
