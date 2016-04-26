package org.itsnat.droid.impl.domparser.drawable;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.dom.drawable.XMLDOMDrawable;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by jmarranz on 31/10/14.
 */
public class XMLDOMDrawableParser extends XMLDOMParser<XMLDOMDrawable>
{
    protected XMLDOMDrawableParser(XMLDOMParserContext xmlDOMParserContext)
    {
        super(xmlDOMParserContext);
    }

    public static XMLDOMDrawableParser createXMLDOMDrawableParser(XMLDOMParserContext xmlDOMParserContext)
    {
        return new XMLDOMDrawableParser(xmlDOMParserContext);
    }

    @Override
    protected boolean isAndroidNSPrefixNeeded()
    {
        return true;
    }

    @Override
    protected DOMElement createElement(String name,DOMElement parent)
    {
        return new DOMElemDrawable(name,(DOMElemDrawable)parent);
    }
}
