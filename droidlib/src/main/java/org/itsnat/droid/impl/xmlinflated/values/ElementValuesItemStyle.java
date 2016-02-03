package org.itsnat.droid.impl.xmlinflated.values;

import org.itsnat.droid.impl.dom.DOMAttr;

/**
 *
 * Created by jmarranz on 27/11/14.
 */
public class ElementValuesItemStyle extends ElementValuesChildNoChildElem
{
    public ElementValuesItemStyle(ElementValuesStyle parentElement, String name, DOMAttr valueAsDOMAttr)
    {
        super("item", parentElement,name,valueAsDOMAttr);
    }

}
