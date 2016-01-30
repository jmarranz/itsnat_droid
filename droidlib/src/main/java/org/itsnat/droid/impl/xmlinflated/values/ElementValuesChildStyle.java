package org.itsnat.droid.impl.xmlinflated.values;

import org.itsnat.droid.impl.dom.DOMAttr;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by jmarranz on 27/11/14.
 */
public class ElementValuesChildStyle extends ElementValuesChild
{
    protected DOMAttr parentAttr; // Puede ser null (no hay atributo parent="...")
    protected ArrayList<DOMAttr> childDOMAttrList;

    public ElementValuesChildStyle(String name,DOMAttr parentAttr,ElementValuesResources parentElement)
    {
        super("style",name,parentElement);
        this.parentAttr = parentAttr;
    }

    public void addChildElementValues(ElementValues child)
    {
        super.addChildElementValues(child);

        if (childDOMAttrList == null) this.childDOMAttrList = new ArrayList<DOMAttr>();

        ElementValuesChildStyleChild item = (ElementValuesChildStyleChild)child;
        DOMAttr domAttr = item.getDOMAttr();
        childDOMAttrList.add(domAttr);
    }

    public DOMAttr getParentAttr()
    {
        return parentAttr;
    }

    public List<DOMAttr> getChildDOMAttrList()
    {
        return childDOMAttrList;
    }
}
