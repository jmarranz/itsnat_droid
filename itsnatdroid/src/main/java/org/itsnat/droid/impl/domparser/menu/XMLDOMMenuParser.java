package org.itsnat.droid.impl.domparser.menu;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.menu.DOMElemMenu;
import org.itsnat.droid.impl.dom.menu.XMLDOMMenu;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by jmarranz on 31/10/14.
 */
public class XMLDOMMenuParser extends XMLDOMParser<XMLDOMMenu>
{
    protected XMLDOMMenuParser(XMLDOMParserContext xmlDOMParserContext)
    {
        super(xmlDOMParserContext);
    }

    public static XMLDOMMenuParser createXMLDOMMenuParser(XMLDOMParserContext xmlDOMParserContext)
    {
        return new XMLDOMMenuParser(xmlDOMParserContext);
    }

    @Override
    protected boolean isAndroidNSPrefixNeeded()
    {
        return true;
    }

    @Override
    protected DOMElement createElement(String tagName,DOMElement parent)
    {
        return new DOMElemMenu(tagName,(DOMElemMenu)parent);
    }
}
