package org.itsnat.droid.impl.domparser.animinterp;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.animinterp.DOMElemInterpolator;
import org.itsnat.droid.impl.dom.animinterp.XMLDOMInterpolator;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.util.MiscUtil;

/**
 * Created by jmarranz on 31/10/14.
 */
public class XMLDOMInterpolatorParser extends XMLDOMParser<XMLDOMInterpolator>
{
    protected XMLDOMInterpolatorParser(XMLDOMParserContext xmlDOMParserContext)
    {
        super(xmlDOMParserContext);
    }

    public static XMLDOMInterpolatorParser createXMLDOMInterpolatorParser(XMLDOMParserContext xmlDOMParserContext)
    {
        return new XMLDOMInterpolatorParser(xmlDOMParserContext);
    }

    public static boolean isInterpolatorRoot(String rootElemName)
    {
        return rootElemName.endsWith("Interpolator");
    }

    @Override
    protected boolean isAndroidNSPrefixNeeded()
    {
        return true;
    }

    @Override
    protected DOMElement createElement(String tagName,DOMElement parent)
    {
        // Interesante ver el código fuente de: AnimationUtils loadInterpolator
        if (parent != null) throw MiscUtil.internalError();
        return new DOMElemInterpolator(tagName);
    }

}
