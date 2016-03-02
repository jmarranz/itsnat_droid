package org.itsnat.droid.impl.xmlinflated.values;

import org.itsnat.droid.impl.dom.DOMAttr;

/**
 *
 * Created by jmarranz on 27/11/14.
 */
public class ElementValuesItemArrayBase extends ElementValuesChildNoChildElem
{
    public ElementValuesItemArrayBase(ElementValuesArrayBase parentElement, DOMAttr valueAsDOMAttr)
    {
        super("item", parentElement,null,valueAsDOMAttr);
    }

}
