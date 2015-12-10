package org.itsnat.droid.impl.dom.layout;

import org.itsnat.droid.impl.dom.DOMElement;

/**
 * Created by jmarranz on 27/10/14.
 */
public class DOMElemMerge extends DOMElement
{
    public DOMElemMerge(String name, DOMElemView parentElement)
    {
        super(name,parentElement);
    }

    @Override
    public DOMElement createDOMElement()
    {
        return new DOMElemMerge(name,(DOMElemView)parentElement);
    }
}
