package org.itsnat.droid.impl.domparser.animlayout;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.animlayout.DOMElemGridLayoutAnimation;
import org.itsnat.droid.impl.dom.animlayout.DOMElemLayoutAnimation;
import org.itsnat.droid.impl.dom.animlayout.XMLDOMLayoutAnimation;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by jmarranz on 31/10/14.
 */
public class XMLDOMLayoutAnimationParser extends XMLDOMParser<XMLDOMLayoutAnimation>
{
    /*
    https://developer.android.com/reference/android/view/animation/LayoutAnimationController.html
    http://stackoverflow.com/questions/3445823/having-trouble-animating-listview-layout
    http://developerlife.com/tutorials/?p=343
    http://stackoverflow.com/questions/19264534/gridlayoutanimation-does-not-works
    */

    protected XMLDOMLayoutAnimationParser(XMLDOMParserContext xmlDOMParserContext)
    {
        super(xmlDOMParserContext);
    }

    public static XMLDOMLayoutAnimationParser createXMLDOMLayoutAnimationParser(XMLDOMParserContext xmlDOMParserContext)
    {
        return new XMLDOMLayoutAnimationParser(xmlDOMParserContext);
    }

    public static boolean isLayoutAnimatorRoot(String rootElemName)
    {
        return ("layoutAnimation".equals(rootElemName) || "gridLayoutAnimation".equals(rootElemName));
    }

    @Override
    protected boolean isAndroidNSPrefixNeeded()
    {
        return true;
    }

    @Override
    protected DOMElement createElement(String tagName,DOMElement parent)
    {
        if ("layoutAnimation".equals(tagName))
        {
            return new DOMElemLayoutAnimation();
        }
        else if ("gridLayoutAnimation".equals(tagName))
        {
            return new DOMElemGridLayoutAnimation();
        }
        else throw new ItsNatDroidException("Unrecognized layoutAnimation tag name: " + tagName);
    }

}
