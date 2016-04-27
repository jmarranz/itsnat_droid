package org.itsnat.droid.impl.domparser.animator;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.animator.DOMElemAnimatorObject;
import org.itsnat.droid.impl.dom.animator.DOMElemAnimatorSet;
import org.itsnat.droid.impl.dom.animator.DOMElemAnimatorValue;
import org.itsnat.droid.impl.dom.animator.XMLDOMAnimator;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by jmarranz on 31/10/14.
 */
public class XMLDOMAnimatorParser extends XMLDOMParser<XMLDOMAnimator>
{
    protected XMLDOMAnimatorParser(XMLDOMParserContext xmlDOMParserContext)
    {
        super(xmlDOMParserContext);
    }

    public static XMLDOMAnimatorParser createXMLDOMAnimatorParser(XMLDOMParserContext xmlDOMParserContext)
    {
        return new XMLDOMAnimatorParser(xmlDOMParserContext);
    }

    @Override
    protected boolean isAndroidNSPrefixNeeded()
    {
        return true;
    }


    @Override
    protected DOMElement createElement(String tagName,DOMElement parent)
    {
        // Interesante ver el código fuente de: AnimatorInflater : Animator createAnimatorFromXml(Context c, XmlPullParser parser)

        DOMElemAnimatorSet parentAnimator = (DOMElemAnimatorSet)parent;
        if ("set".equals(tagName))
        {
            return new DOMElemAnimatorSet(parentAnimator);
        }
        else if ("objectAnimator".equals(tagName))
        {
            return new DOMElemAnimatorObject(parentAnimator);
        }
        else if ("animator".equals(tagName))
        {
            return new DOMElemAnimatorValue(parentAnimator);
        }
        else throw new ItsNatDroidException("Unrecognized animator tag name: " + tagName);
    }

}
