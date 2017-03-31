package org.itsnat.droid.impl.domparser.values;

import org.itsnat.droid.impl.dom.values.XMLDOMValues;
import org.itsnat.droid.impl.domparser.ResourceCacheByMarkupAndResDescNoLayout;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by jmarranz on 24/04/2016.
 */
public class ResourceCacheByMarkupAndResDescValues extends ResourceCacheByMarkupAndResDescNoLayout<XMLDOMValues,XMLDOMValuesParser>
{
    public ResourceCacheByMarkupAndResDescValues()
    {
    }

    @Override
    protected XMLDOMValues createXMLDOMInstance()
    {
        return new XMLDOMValues();
    }

    @Override
    protected XMLDOMValuesParser createXMLDOMParserInstance(XMLDOMParserContext xmlDOMParserContext)
    {
        return XMLDOMValuesParser.createXMLDOMValuesParser(xmlDOMParserContext);
    }
}
