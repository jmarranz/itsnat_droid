package org.itsnat.droid.impl.dom;

/**
 * Created by jmarranz on 20/01/2016.
 */
public class ParsedResourceXMLDOM extends ParsedResource
{
    protected XMLDOM xmlDOM;

    public ParsedResourceXMLDOM(XMLDOM xmlDOM)
    {
        this.xmlDOM = xmlDOM;
    }

    public XMLDOM getXMLDOM()
    {
        return xmlDOM;
    }

    @Override
    public ParsedResource copy()
    {
        return new ParsedResourceXMLDOM(xmlDOM);
    }
}
