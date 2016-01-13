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
    protected ArrayList<DOMAttr> domAttrList;

    public ElementValuesChildStyle(String name,ElementValuesResources parentElement)
    {
        super("style",name,parentElement);
    }

    public void addChildElementValues(ElementValues child)
    {
        super.addChildElementValues(child);

        if (domAttrList == null) domAttrList = new ArrayList<DOMAttr>();

        ElementValuesChildStyleChild item = (ElementValuesChildStyleChild)child;
        DOMAttr domAttr = item.getDOMAttr();
        domAttrList.add(domAttr);
    }

    public List<DOMAttr> getDOMAttrList()
    {
        return domAttrList;
    }
}
