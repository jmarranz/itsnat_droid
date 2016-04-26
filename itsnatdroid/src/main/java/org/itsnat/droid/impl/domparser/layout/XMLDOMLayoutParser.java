package org.itsnat.droid.impl.domparser.layout;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.dom.layout.DOMElemLayout;
import org.itsnat.droid.impl.dom.layout.DOMElemMerge;
import org.itsnat.droid.impl.dom.layout.DOMElemView;
import org.itsnat.droid.impl.dom.layout.XMLDOMLayout;
import org.itsnat.droid.impl.domparser.XMLDOMParser;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.util.NamespaceUtil;


/**
 * Created by jmarranz on 27/10/14.
 */
public abstract class XMLDOMLayoutParser extends XMLDOMParser<XMLDOMLayout>
{
    public enum LayoutType { PAGE, PAGE_FRAGMENT, STANDALONE };

    protected XMLDOMLayoutParser(XMLDOMParserContext xmlDOMParserContext)
    {
        super(xmlDOMParserContext);
    }

    public static XMLDOMLayoutParser createXMLDOMLayoutParser(String itsNatServerVersion,LayoutType layoutType,XMLDOMParserContext xmlDOMParserContext)
    {
        XMLDOMLayoutParser layoutParser;
        if (layoutType == LayoutType.PAGE)
        {
            if (itsNatServerVersion != null)
                layoutParser = new XMLDOMLayoutParserPageItsNat(xmlDOMParserContext,itsNatServerVersion);
            else
                layoutParser = new XMLDOMLayoutParserPageNotItsNat(xmlDOMParserContext);
        }
        else if (layoutType == LayoutType.PAGE_FRAGMENT) // Los fragmentos pueden usarse en páginas generadas por ItsNat y no generadas por ItsNat (itsNatServerVersion es indiferente)
            layoutParser = new XMLDOMLayoutParserPageFragment(xmlDOMParserContext);
        else if (layoutType == LayoutType.STANDALONE)
            layoutParser = new XMLDOMLayoutParserStandalone(xmlDOMParserContext);
        else
            layoutParser = null; // Internal Error

        return layoutParser;
    }

    @Override
    protected boolean isAndroidNSPrefixNeeded()
    {
        return true;
    }


    public abstract XMLDOMLayout createXMLDOMLayout();


    @Override
    protected void prepareDOMAttrToLoadResource(DOMAttr attrib, XMLDOM xmlDOMParent)
    {
        if (!(attrib instanceof DOMAttrRemote))
        {
            if (NamespaceUtil.XMLNS_ITSNATDROID_RESOURCE.equals(attrib.getNamespaceURI()))
                throw new ItsNatDroidException("Values of attributes with namespace " + NamespaceUtil.XMLNS_ITSNATDROID_RESOURCE + " must reference remote resources");
            // Los atributos con namespace XMLNS_ITSNATDROID_RESOURCE sirven para que sean detectados en el parseo como atributos remotos y se cargue automáticamente el recurso asociado
            // para poder ser accedidos via ItsNatResources.getLayout() etc e ignorados en el proceso normal de atributos
        }

        super.prepareDOMAttrToLoadResource(attrib,xmlDOMParent);
    }

    @Override
    protected DOMElement createElement(String name,DOMElement parent)
    {
        if ("merge".equals(name))
        {
            if (parent != null) throw new ItsNatDroidException("<merge> only can be used as a root element of the XML layout");
            return new DOMElemMerge(name, null);
        }
        else
            return new DOMElemView(name,(DOMElemLayout)parent);
    }
}
