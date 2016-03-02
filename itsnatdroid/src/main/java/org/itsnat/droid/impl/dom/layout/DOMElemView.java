package org.itsnat.droid.impl.dom.layout;

/**
 * Created by jmarranz on 27/10/14.
 */
public class DOMElemView extends DOMElemLayout
{
    public DOMElemView(String tagName, DOMElemLayout parentElement)
    {
        super(tagName,parentElement);
    }

    public DOMElemView(DOMElemLayout toCopy)
    {
        super(toCopy);
    }

}
