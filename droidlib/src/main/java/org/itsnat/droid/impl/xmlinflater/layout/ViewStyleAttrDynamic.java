package org.itsnat.droid.impl.xmlinflater.layout;

import org.itsnat.droid.impl.dom.DOMAttr;

import java.util.List;

/**
 * Created by jmarranz on 12/01/2016.
 */
public class ViewStyleAttrDynamic extends ViewStyleAttr
{
    protected DOMAttr domAttrParent;
    protected List<DOMAttr> domAttrList;

    public ViewStyleAttrDynamic(DOMAttr domAttrParent,List<DOMAttr> domAttrList)
    {
        this.domAttrParent = domAttrParent;
        this.domAttrList = domAttrList;
    }

    public DOMAttr getDOMAttrParentStyle()
    {
        return domAttrParent;
    }

    public List<DOMAttr> getDOMAttrItemList()
    {
        return domAttrList;
    }
}
