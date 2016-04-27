package org.itsnat.droid.impl.dom.animator;

import org.itsnat.droid.impl.dom.DOMElement;

/**
 * Created by jmarranz on 21/03/2016.
 */
public abstract class DOMElemAnimator extends DOMElement
{
    public DOMElemAnimator(String tagName, DOMElemAnimatorSet parentElement)
    {
        super(tagName, parentElement);
    }
}
