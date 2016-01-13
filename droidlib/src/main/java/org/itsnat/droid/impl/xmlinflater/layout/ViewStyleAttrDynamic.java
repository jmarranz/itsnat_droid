package org.itsnat.droid.impl.xmlinflater.layout;

import org.itsnat.droid.impl.dom.DOMAttr;

import java.util.List;

/**
 * Created by jmarranz on 12/01/2016.
 */
public class ViewStyleAttrDynamic extends ViewStyleAttr
{
    protected List<DOMAttr> domAttrList;

    public ViewStyleAttrDynamic(List<DOMAttr> domAttrList)
    {
        this.domAttrList = domAttrList;
    }

    public List<DOMAttr> getDOMAttrList()
    {
        return domAttrList;
    }
}
