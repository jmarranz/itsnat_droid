package org.itsnat.droid.impl.xmlinflater;

import android.content.Context;

import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by Jose on 09/11/2015.
 */
public abstract class AttrContext
{
    protected XMLInflater xmlInflater;
    protected XMLDOMParserContext xmlDOMParserContext;

    public AttrContext(XMLInflater xmlInflater,XMLDOMParserContext xmlDOMParserContext)
    {
        this.xmlInflater = xmlInflater;
        this.xmlDOMParserContext = xmlDOMParserContext;
    }

    public Context getContext()
    {
        return xmlInflater.getContext();
    }

    public XMLInflater getXMLInflater()
    {
        return xmlInflater;
    }

    public XMLDOMParserContext getXMLDOMParserContext()
    {
        return xmlDOMParserContext;
    }
}
