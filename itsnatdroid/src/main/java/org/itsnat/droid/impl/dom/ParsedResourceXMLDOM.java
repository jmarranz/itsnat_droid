package org.itsnat.droid.impl.dom;

/**
 * Created by jmarranz on 20/01/2016.
 */
public class ParsedResourceXMLDOM<T extends XMLDOM> extends ParsedResource
{
    protected T xmlDOM;

    public ParsedResourceXMLDOM(T xmlDOM)
    {
        this.xmlDOM = xmlDOM;
    }

    public T getXMLDOM()
    {
        return xmlDOM;
    }

    @Override
    public ParsedResource copy()
    {
        return new ParsedResourceXMLDOM<T>(xmlDOM);
    }
}
