package org.itsnat.droid.impl.dom.anim;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMElement;

/**
 * Created by jmarranz on 21/03/2016.
 */
public class DOMElemAnimationSingle extends DOMElemAnimation
{
    public DOMElemAnimationSingle(String tagName, DOMElemAnimationSet parentElement)
    {
        super(tagName, parentElement);
    }

    @Override
    public void addChildDOMElement(DOMElement domElement)
    {
        throw new ItsNatDroidException("This XML element " + tagName + " has no child elements");
    }
}
