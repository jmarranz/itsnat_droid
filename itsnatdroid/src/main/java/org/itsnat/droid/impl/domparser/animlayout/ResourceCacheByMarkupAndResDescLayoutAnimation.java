package org.itsnat.droid.impl.domparser.animlayout;

import org.itsnat.droid.impl.dom.animlayout.XMLDOMLayoutAnimation;
import org.itsnat.droid.impl.domparser.ResourceCacheByMarkupAndResDesc;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by jmarranz on 24/04/2016.
 */
public class ResourceCacheByMarkupAndResDescLayoutAnimation extends ResourceCacheByMarkupAndResDesc<XMLDOMLayoutAnimation,XMLDOMLayoutAnimationParser>
{
    public ResourceCacheByMarkupAndResDescLayoutAnimation()
    {
    }

    @Override
    protected XMLDOMLayoutAnimation createXMLDOMInstance()
    {
        return new XMLDOMLayoutAnimation();
    }

    @Override
    protected XMLDOMLayoutAnimationParser createXMLDOMParserInstance(XMLDOMParserContext xmlDOMParserContext)
    {
        return XMLDOMLayoutAnimationParser.createXMLDOMLayoutAnimationParser(xmlDOMParserContext);
    }
}
