package org.itsnat.droid.impl.xmlinflater.values.classtree;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.values.DOMElemValuesArrayBase;
import org.itsnat.droid.impl.dom.values.DOMElemValuesItemArrayBase;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesArrayBase;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesItemArrayBase;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesResources;
import org.itsnat.droid.impl.xmlinflater.values.ClassDescValuesMgr;

import java.util.List;

/**
 * Created by jmarranz on 07/01/2016.
 */
public class ClassDescValuesArrayBase extends ClassDescValues<ElementValuesArrayBase>
{
    public ClassDescValuesArrayBase(ClassDescValuesMgr classMgr,String tagName)
    {
        super(classMgr, tagName, null); // tagName = string-array, integer-array, array
    }

    public ElementValuesArrayBase createElementValuesArrayBase(DOMElemValuesArrayBase domElement, ElementValuesResources parentChildValues)
    {
        String tagName = domElement.getTagName();
        String name = domElement.getNameAttr();

        ElementValuesArrayBase elementValuesArrayBase = new ElementValuesArrayBase(tagName,name,parentChildValues);

        processChildElementValuesItemArrayBase(domElement, elementValuesArrayBase);

        return elementValuesArrayBase;
    }

    private void processChildElementValuesItemArrayBase(DOMElemValuesArrayBase domElemParent, ElementValuesArrayBase parentChildValues)
    {
        List<DOMElement> childDOMElemList = domElemParent.getChildDOMElementList();
        if (childDOMElemList == null) return;

        parentChildValues.initChildElementValuesList(childDOMElemList.size());
        for (DOMElement childDOMElem : childDOMElemList)
        {
            ElementValuesItemArrayBase childElemValues = inflateNextElement((DOMElemValuesItemArrayBase)childDOMElem,parentChildValues);
            parentChildValues.addChildElementValues(childElemValues);
        }
    }

    private ElementValuesItemArrayBase inflateNextElement(DOMElemValuesItemArrayBase domElement,ElementValuesArrayBase parentChildValues)
    {
        DOMAttr valueAsDOMAttr = domElement.getValueAsDOMAttr();
        return new ElementValuesItemArrayBase(parentChildValues,valueAsDOMAttr);
    }
}
