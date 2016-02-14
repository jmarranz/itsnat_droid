package org.itsnat.droid.impl.dom.values;

import org.itsnat.droid.impl.dom.DOMElement;

/**
 * Created by jmarranz on 3/11/14.
 */
public abstract class DOMElemValues extends DOMElement
{
    public DOMElemValues(String tagName, DOMElemValues parentElement)
    {
        super(tagName, parentElement);
    }

}
