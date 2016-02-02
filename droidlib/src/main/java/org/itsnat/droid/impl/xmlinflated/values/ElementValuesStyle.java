package org.itsnat.droid.impl.xmlinflated.values;

import org.itsnat.droid.impl.dom.DOMAttr;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by jmarranz on 27/11/14.
 */
public class ElementValuesStyle extends ElementValuesChild
{
    protected DOMAttr parentAttr; // Puede ser null (no hay atributo parent="...")
    protected ArrayList<DOMAttr> childDOMAttrValueList;

    public ElementValuesStyle(String name, DOMAttr parentAttr, ElementValuesResources parentElement)
    {
        super("style", name, parentElement);
        this.parentAttr = parentAttr;
    }

    public void addChildElementValues(ElementValues child)
    {
        super.addChildElementValues(child);

        if (childDOMAttrValueList == null) this.childDOMAttrValueList = new ArrayList<DOMAttr>();

        ElementValuesItemStyle item = (ElementValuesItemStyle)child;
        DOMAttr domAttrValue = item.getValueAsDOMAttr();
        childDOMAttrValueList.add(domAttrValue);
    }

    public DOMAttr getParentAttr()
    {
        return parentAttr;
    }

    public List<DOMAttr> getChildDOMAttrValueList()
    {
        return childDOMAttrValueList;
    }
}
