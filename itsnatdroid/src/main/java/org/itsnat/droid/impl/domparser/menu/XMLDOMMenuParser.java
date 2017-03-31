package org.itsnat.droid.impl.domparser.menu;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.menu.DOMElemGroup;
import org.itsnat.droid.impl.dom.menu.DOMElemItem;
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
        if ("menu".equals(tagName))
        {
            if (parent == null)
                return new DOMElemMenu(false,null); // root
            String parentTagName = parent.getTagName();
            if ("item".equals(parentTagName))
                return new DOMElemMenu(true,parent);
            else throw new ItsNatDroidException("Unrecognized super tag name of <menu>: " + parentTagName);
        }
        else if ("item".equals(tagName))
        {
            return new DOMElemItem(parent);
        }
        else if ("group".equals(tagName))
        {
            return new DOMElemGroup(parent);
        }
        else throw new ItsNatDroidException("Unrecognized menu tag name: " + tagName);
    }
}
