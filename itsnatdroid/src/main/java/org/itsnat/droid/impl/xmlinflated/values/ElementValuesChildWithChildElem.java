package org.itsnat.droid.impl.xmlinflated.values;

import org.itsnat.droid.impl.dom.DOMAttr;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by jmarranz on 27/11/14.
 */
public abstract class ElementValuesChildWithChildElem extends ElementValuesChild
{
    protected ArrayList<DOMAttr> childDOMAttrValueList;

    public ElementValuesChildWithChildElem(String tagName,String name, ElementValuesResources parentElement)
    {
        super(tagName, name, parentElement);
    }

    public void addChildElementValues(ElementValues child)
    {
        super.addChildElementValues(child);

        if (childDOMAttrValueList == null) this.childDOMAttrValueList = new ArrayList<DOMAttr>();

        ElementValuesChildNoChildElem item = (ElementValuesChildNoChildElem)child;
        DOMAttr domAttrValue = item.getValueAsDOMAttr();
        childDOMAttrValueList.add(domAttrValue);
    }

    public List<DOMAttr> getChildDOMAttrValueList()
    {
        return childDOMAttrValueList;
    }
}
