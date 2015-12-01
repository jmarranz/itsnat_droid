package org.itsnat.droid.impl.dom.layout;

import org.itsnat.droid.impl.dom.DOMElement;

/**
 * Created by jmarranz on 27/10/14.
 */
public class DOMInclude extends DOMElement
{
    protected String layout;

    public DOMInclude(String name, DOMView parentElement)
    {
        super(name,parentElement);
    }

    public String getLayout()
    {
        return layout;
    }

    public void setLayout(String layout)
    {
        this.layout = layout;
    }

}
