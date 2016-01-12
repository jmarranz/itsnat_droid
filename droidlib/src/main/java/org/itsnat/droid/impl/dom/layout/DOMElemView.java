package org.itsnat.droid.impl.dom.layout;

/**
 * Created by jmarranz on 27/10/14.
 */
public class DOMElemView extends DOMElemLayout
{
    public DOMElemView(String name, DOMElemLayout parentElement)
    {
        super(name,parentElement);
    }

    public DOMElemView(DOMElemLayout toCopy)
    {
        super(toCopy);
    }

    /*
    public DOMView getParentDOMView()
    {
        return (DOMView) getParentDOMElement();
    }
    */

    /*
    public void addChildView(DOMView domView)
    {
        super.addChildDOMElement(domView);
    }
    */
}
