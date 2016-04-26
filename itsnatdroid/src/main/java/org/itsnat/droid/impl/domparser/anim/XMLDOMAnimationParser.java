package org.itsnat.droid.impl.domparser.anim;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.anim.DOMElemAnimation;
import org.itsnat.droid.impl.dom.anim.XMLDOMAnimation;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by jmarranz on 31/10/14.
 */
public class XMLDOMAnimationParser extends XMLDOMParser<XMLDOMAnimation>
{
    protected XMLDOMAnimationParser(XMLDOMParserContext xmlDOMParserContext)
    {
        super(xmlDOMParserContext);
    }

    public static XMLDOMAnimationParser createXMLDOMAnimationParser(XMLDOMParserContext xmlDOMParserContext)
    {
        return new XMLDOMAnimationParser(xmlDOMParserContext);
    }

    @Override
    protected boolean isAndroidNSPrefixNeeded()
    {
        return true;
    }

    @Override
    protected DOMElement createElement(String name,DOMElement parent)
    {
        return new DOMElemAnimation(name,(DOMElemAnimation)parent);
    }


}
