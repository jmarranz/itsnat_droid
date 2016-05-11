package org.itsnat.droid.impl.domparser.animinterp;

import org.itsnat.droid.impl.dom.animinterp.XMLDOMInterpolator;
import org.itsnat.droid.impl.domparser.ResourceCacheByMarkupAndResDesc;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by jmarranz on 24/04/2016.
 */
public class ResourceCacheByMarkupAndResDescInterpolator extends ResourceCacheByMarkupAndResDesc<XMLDOMInterpolator,XMLDOMInterpolatorParser>
{
    public ResourceCacheByMarkupAndResDescInterpolator()
    {
    }

    @Override
    protected XMLDOMInterpolator createXMLDOMInstance()
    {
        return new XMLDOMInterpolator();
    }

    @Override
    protected XMLDOMInterpolatorParser createXMLDOMParserInstance(XMLDOMParserContext xmlDOMParserContext)
    {
        return XMLDOMInterpolatorParser.createXMLDOMInterpolatorParser(xmlDOMParserContext);
    }
}
