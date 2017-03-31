package org.itsnat.droid.impl.domparser.menu;

import org.itsnat.droid.impl.dom.menu.XMLDOMMenu;
import org.itsnat.droid.impl.domparser.ResourceCacheByMarkupAndResDescNoLayout;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by jmarranz on 24/04/2016.
 */
public class ResourceCacheByMarkupAndResDescMenu extends ResourceCacheByMarkupAndResDescNoLayout<XMLDOMMenu,XMLDOMMenuParser>
{
    public ResourceCacheByMarkupAndResDescMenu()
    {
    }

    @Override
    protected XMLDOMMenu createXMLDOMInstance()
    {
        return new XMLDOMMenu();
    }

    @Override
    protected XMLDOMMenuParser createXMLDOMParserInstance(XMLDOMParserContext xmlDOMParserContext)
    {
        return XMLDOMMenuParser.createXMLDOMMenuParser(xmlDOMParserContext);
    }
}
