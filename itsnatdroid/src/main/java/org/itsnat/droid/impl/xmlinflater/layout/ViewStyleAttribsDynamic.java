package org.itsnat.droid.impl.xmlinflater.layout;

import org.itsnat.droid.impl.dom.DOMAttr;

import java.util.List;

/**
 * Created by jmarranz on 12/01/2016.
 */
public class ViewStyleAttribsDynamic extends ViewStyleAttribs
{
    protected DOMAttr domAttrParent;
    protected List<DOMAttr> domAttrValueList;

    public ViewStyleAttribsDynamic(DOMAttr domAttrParent, List<DOMAttr> domAttrValueList)
    {
        this.domAttrParent = domAttrParent;
        this.domAttrValueList = domAttrValueList;
    }

    public DOMAttr getDOMAttrParentStyle()
    {
        return domAttrParent;
    }

    public List<DOMAttr> getDOMAttrItemList()
    {
        return domAttrValueList;
    }

}
