package org.itsnat.droid.impl.dom.layout;

import org.itsnat.droid.impl.dom.DOMElement;

/**
 * Created by jmarranz on 27/10/14.
 */
public class DOMElemView extends DOMElement
{
    protected String styleAttr;

    public DOMElemView(String name, DOMElement parentElement)
    {
        super(name,parentElement);
    }

    public DOMElemView(DOMElement toCopy)
    {
        super(toCopy);
        if (toCopy instanceof DOMElemView)
            this.styleAttr = ((DOMElemView)toCopy).styleAttr;
    }

    @Override
    protected void clone(DOMElement clone,boolean cloneChildren)
    {
        super.clone(clone,cloneChildren);

        ((DOMElemView)clone).styleAttr = styleAttr;
    }

    @Override
    public DOMElement createDOMElement()
    {
        return new DOMElemView(name,parentElement);
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
