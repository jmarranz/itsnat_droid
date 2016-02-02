package org.itsnat.droid.impl.xmlinflated.values;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.NamespaceUtil;

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
