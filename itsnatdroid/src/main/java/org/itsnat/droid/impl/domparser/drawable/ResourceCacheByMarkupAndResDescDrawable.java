package org.itsnat.droid.impl.domparser.drawable;

import org.itsnat.droid.impl.dom.drawable.XMLDOMDrawable;
import org.itsnat.droid.impl.domparser.ResourceCacheByMarkupAndResDescNoLayout;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by jmarranz on 24/04/2016.
 */
public class ResourceCacheByMarkupAndResDescDrawable extends ResourceCacheByMarkupAndResDescNoLayout<XMLDOMDrawable,XMLDOMDrawableParser>
{
    public ResourceCacheByMarkupAndResDescDrawable()
    {
    }

    @Override
    protected XMLDOMDrawable createXMLDOMInstance()
    {
        return new XMLDOMDrawable();
    }

    @Override
    protected XMLDOMDrawableParser createXMLDOMParserInstance(XMLDOMParserContext xmlDOMParserContext)
    {
        return XMLDOMDrawableParser.createXMLDOMDrawableParser(xmlDOMParserContext);
    }
}
