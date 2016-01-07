package org.itsnat.droid.impl.dom.values;

import org.itsnat.droid.impl.dom.DOMElement;

/**
 * Created by jmarranz on 3/11/14.
 */
public class DOMElemValues extends DOMElement
{
    protected String text; // En el caso de existir reconocido child text node hijo como único tipo de child node

    public DOMElemValues(String name, DOMElemValues parentElement)
    {
        super(name, parentElement);
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
