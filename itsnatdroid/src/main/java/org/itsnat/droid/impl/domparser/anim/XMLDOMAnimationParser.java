package org.itsnat.droid.impl.domparser.anim;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.anim.DOMElemAnimationSet;
import org.itsnat.droid.impl.dom.anim.DOMElemAnimationSingle;
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
    protected DOMElement createElement(String tagName,DOMElement parent)
    {
        // Interesante ver el código fuente de: AnimationUtils loadAnimation

        DOMElemAnimationSet parentAnimation = (DOMElemAnimationSet)parent;

        if ("set".equals(tagName))
        {
            return new DOMElemAnimationSet(parentAnimation);
        }
        else if ("alpha".equals(tagName) || "scale".equals(tagName) || "rotate".equals(tagName) || "translate".equals(tagName))
        {
            return new DOMElemAnimationSingle(tagName,parentAnimation);
        }
        else throw new ItsNatDroidException("Unrecognized animator tag name: " + tagName);
    }


}
