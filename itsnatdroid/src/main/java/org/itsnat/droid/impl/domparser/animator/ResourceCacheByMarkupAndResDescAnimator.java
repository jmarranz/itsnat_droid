package org.itsnat.droid.impl.domparser.animator;

import org.itsnat.droid.impl.dom.animator.XMLDOMAnimator;
import org.itsnat.droid.impl.domparser.ResourceCacheByMarkupAndResDesc;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by jmarranz on 24/04/2016.
 */
public class ResourceCacheByMarkupAndResDescAnimator extends ResourceCacheByMarkupAndResDesc<XMLDOMAnimator,XMLDOMAnimatorParser>
{
    public ResourceCacheByMarkupAndResDescAnimator()
    {
    }

    @Override
    protected XMLDOMAnimator createXMLDOMInstance()
    {
        return new XMLDOMAnimator();
    }

    @Override
    protected XMLDOMAnimatorParser createXMLDOMParserInstance(XMLDOMParserContext xmlDOMParserContext)
    {
        return XMLDOMAnimatorParser.createXMLDOMAnimatorParser(xmlDOMParserContext);
    }
}
