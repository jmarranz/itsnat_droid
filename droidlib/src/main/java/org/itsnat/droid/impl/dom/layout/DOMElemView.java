package org.itsnat.droid.impl.dom.layout;

import org.itsnat.droid.impl.dom.DOMElement;

/**
 * Created by jmarranz on 27/10/14.
 */
public class DOMElemView extends DOMElemLayout
{
    protected String styleAttr;

    public DOMElemView(String name, DOMElemLayout parentElement)
    {
        super(name,parentElement);
    }

    public DOMElemView(DOMElemLayout toCopy)
    {
        super(toCopy);
        if (toCopy instanceof DOMElemView)
            this.styleAttr = ((DOMElemView)toCopy).styleAttr;
    }

    /*
    public DOMView getParentDOMView()
    {
        return (DOMView) getParentDOMElement();
    }
    */

    public String getStyleAttr()
    {
        return styleAttr;
    }

    public void setStyleAttr(String styleAttr)
    {
        this.styleAttr = styleAttr;
    }

    /*
    public void addChildView(DOMView domView)
    {
        super.addChildDOMElement(domView);
    }
    */
}
