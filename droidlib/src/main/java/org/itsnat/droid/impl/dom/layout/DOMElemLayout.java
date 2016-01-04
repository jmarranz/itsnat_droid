package org.itsnat.droid.impl.dom.layout;

import org.itsnat.droid.impl.dom.DOMElement;

/**
 * Created by jmarranz on 04/01/2016.
 */
public abstract class DOMElemLayout extends DOMElement
{
    public DOMElemLayout(String name, DOMElemLayout parentElement)
    {
        super(name,parentElement);
    }

    public DOMElemLayout(DOMElemLayout toCopy)
    {
        super(toCopy);
    }

}
