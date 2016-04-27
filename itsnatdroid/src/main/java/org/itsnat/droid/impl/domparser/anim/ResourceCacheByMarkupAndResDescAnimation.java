package org.itsnat.droid.impl.domparser.anim;

import org.itsnat.droid.impl.dom.anim.XMLDOMAnimation;
import org.itsnat.droid.impl.domparser.ResourceCacheByMarkupAndResDesc;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by jmarranz on 24/04/2016.
 */
public class ResourceCacheByMarkupAndResDescAnimation extends ResourceCacheByMarkupAndResDesc<XMLDOMAnimation,XMLDOMAnimationParser>
{
    public ResourceCacheByMarkupAndResDescAnimation()
    {
    }

    @Override
    protected XMLDOMAnimation createXMLDOMInstance()
    {
        return new XMLDOMAnimation();
    }

    @Override
    protected XMLDOMAnimationParser createXMLDOMParserInstance(XMLDOMParserContext xmlDOMParserContext)
    {
        return XMLDOMAnimationParser.createXMLDOMAnimationParser(xmlDOMParserContext);
    }
}
