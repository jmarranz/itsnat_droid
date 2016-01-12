package org.itsnat.droid.impl.xmlinflater.layout;

import org.itsnat.droid.impl.dom.DOMAttr;

import java.util.List;

/**
 * Created by jmarranz on 12/01/2016.
 */
public class ViewStyleAttrList extends ViewStyle
{
    protected List<DOMAttr> domAttrList;

    public ViewStyleAttrList(List<DOMAttr> domAttrList)
    {
        this.domAttrList = domAttrList;
    }

    public List<DOMAttr> getDOMAttrList()
    {
        return domAttrList;
    }
}
