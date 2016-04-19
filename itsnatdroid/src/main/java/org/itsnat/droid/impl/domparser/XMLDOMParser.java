package org.itsnat.droid.impl.domparser;

import android.content.res.AssetManager;
import android.util.Xml;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.HttpRequestResultOKImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrAsset;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.dom.ParsedResourceImage;
import org.itsnat.droid.impl.dom.ParsedResourceXMLDOM;
import org.itsnat.droid.impl.dom.ResourceDescAsset;
import org.itsnat.droid.impl.dom.ResourceDescDynamic;
import org.itsnat.droid.impl.dom.ResourceDescRemote;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.dom.values.XMLDOMValues;
import org.itsnat.droid.impl.domparser.layout.XMLDOMLayoutParser;
import org.itsnat.droid.impl.util.IOUtil;
import org.itsnat.droid.impl.util.MimeUtil;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.util.StringUtil;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * Created by jmarranz on 31/10/14.
 */
public abstract class XMLDOMParser
{
    protected final XMLDOMParserContext xmlDOMParserContext;

    public XMLDOMParser(XMLDOMParserContext xmlDOMParserContext)
    {
        this.xmlDOMParserContext = xmlDOMParserContext;
    }

    protected static XmlPullParser newPullParser(Reader input)
    {
        try
        {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            parser.setInput(input);
            return parser;
        }
        catch (XmlPullParserException ex)
        {
            throw new ItsNatDroidException(ex);
        }
    }

    public DOMElement parseRootElement(String rootElemName, XmlPullParser parser, XMLDOM xmlDOM) throws IOException, XmlPullParserException
    {
        int nsStart = parser.getNamespaceCount(parser.getDepth() - 1);
        int nsEnd = parser.getNamespaceCount(parser.getDepth());
        for (int i = nsStart; i < nsEnd; i++)
        {
            String prefix = parser.getNamespacePrefix(i);
            String ns = parser.getNamespaceUri(i);
            xmlDOM.addNamespace(prefix, ns);
        }

        if (isAndroidNSPrefixNeeded() && xmlDOM.getAndroidNSPrefix() == null)
            throw new ItsNatDroidException("Missing android namespace declaration in the root element of the XML");

        DOMElement rootElement = createRootElementAndFillAttributes(rootElemName, parser, xmlDOM);
        xmlDOM.setRootElement(rootElement);

        processChildElements(rootElement, parser, xmlDOM);

        return rootElement;
    }


    protected abstract boolean isAndroidNSPrefixNeeded();

    protected DOMElement createRootElementAndFillAttributes(String name,XmlPullParser parser,XMLDOM xmlDOM) throws IOException, XmlPullParserException
    {
        DOMElement rootElement = createElement(name, null);

        fillAttributesAndAddElement(null, rootElement, parser, xmlDOM);

        return rootElement;
    }

    protected DOMElement createElementAndFillAttributesAndAdd(String name, DOMElement parentElement, XmlPullParser parser,XMLDOM xmlDOM) throws XmlPullParserException
    {
        // parentElement es null en el caso de parseo de fragment
        DOMElement element = createElement(name,parentElement);

        fillAttributesAndAddElement(parentElement, element, parser, xmlDOM);

        return element;
    }

    protected void fillAttributesAndAddElement(DOMElement parentElement, DOMElement element,XmlPullParser parser,XMLDOM xmlDOM) throws XmlPullParserException
    {
        fillElementAttributes(element, parser, xmlDOM);
        if (parentElement != null) parentElement.addChildDOMElement(element);
    }

    protected void fillElementAttributes(DOMElement element,XmlPullParser parser,XMLDOM xmlDOM) throws XmlPullParserException
    {
        if (element.getParentDOMElement() != null) // No es root
        {
            int nsStart = parser.getNamespaceCount(parser.getDepth() - 1);
            int nsEnd = parser.getNamespaceCount(parser.getDepth());
            if (nsStart != nsEnd)
                throw new ItsNatDroidException("Namespaces only must be defined in root element");
        }

        int len = parser.getAttributeCount();
        if (len == 0) return;

        element.initDOMAttribMap(len);
        for (int i = 0; i < len; i++)
        {
            String namespaceURI = parser.getAttributeNamespace(i);
            if ("".equals(namespaceURI)) namespaceURI = null; // Por estandarizar
            String name = parser.getAttributeName(i); // El nombre devuelto no contiene el namespace
            String value = parser.getAttributeValue(i);
            addDOMAttr(element, namespaceURI, name, value, xmlDOM);
        }
    }

    protected void processChildElements(DOMElement parentElement,XmlPullParser parser,XMLDOM xmlDOM) throws IOException, XmlPullParserException
    {
        DOMElement childView = parseNextChild(parentElement, parser, xmlDOM);
        while (childView != null)
        {
            childView = parseNextChild(parentElement,parser, xmlDOM);
        }
    }

    private DOMElement parseNextChild(DOMElement parentElement,XmlPullParser parser,XMLDOM xmlDOM) throws IOException, XmlPullParserException
    {
        while (parser.next() != XmlPullParser.END_TAG)
        {
            if (parser.getEventType() != XmlPullParser.START_TAG) // Nodo de texto etc
                continue;

            String name = parser.getName(); // viewName lo normal es que sea un nombre corto por ej RelativeLayout

            DOMElement element = processElement(name, parentElement, parser, xmlDOM);
            if (element == null) continue; // Se ignora
            return element;
        }
        return null;
    }

    protected DOMElement processElement(String name, DOMElement parentElement, XmlPullParser parser,XMLDOM xmlDOM) throws IOException, XmlPullParserException
    {
        DOMElement element = createElementAndFillAttributesAndAdd(name, parentElement, parser, xmlDOM);
        processChildElements(element,parser, xmlDOM);
        return element;
    }

    protected static String findAttributeFromParser(String namespaceURI, String name, XmlPullParser parser)
    {
        for(int i = 0; i < parser.getAttributeCount(); i++)
        {
            String currNamespaceURI = parser.getAttributeNamespace(i);
            if ("".equals(currNamespaceURI)) currNamespaceURI = null; // Por estandarizar
            if (!MiscUtil.equalsNullAllowed(currNamespaceURI, namespaceURI)) continue;
            String currName = parser.getAttributeName(i); // El nombre devuelto no contiene el namespace
            if (!name.equals(currName)) continue;
            String value = parser.getAttributeValue(i);
            return value;
        }
        return null;
    }

    protected static String getRootElementName(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        while (parser.next() != XmlPullParser.END_TAG)
        {
            if (parser.getEventType() != XmlPullParser.START_TAG) // Nodo de texto etc
                continue;

            return parser.getName();
        }

        throw new ItsNatDroidException("INTERNAL ERROR: NO ROOT VIEW");
    }

    protected DOMAttr addDOMAttr(DOMElement element, String namespaceURI, String name, String value, XMLDOM xmlDOMParent)
    {
        DOMAttr attrib = DOMAttr.createDOMAttr(namespaceURI, name, value);
        addDOMAttr(element,attrib,xmlDOMParent);
        return attrib;
    }

    protected void addDOMAttr(DOMElement element, DOMAttr attrib, XMLDOM xmlDOMParent)
    {
        prepareDOMAttrToLoadResource(attrib, xmlDOMParent);
        element.setDOMAttribute(attrib);
    }

    protected void prepareDOMAttrToLoadResource(DOMAttr attrib, XMLDOM xmlDOMParent)
    {
        if (attrib instanceof DOMAttrRemote)
        {
            xmlDOMParent.addDOMAttrRemote((DOMAttrRemote) attrib);
        }
        else if (attrib instanceof DOMAttrAsset)
        {
            DOMAttrAsset assetAttr = (DOMAttrAsset)attrib;

            prepareResourceDescAssetToLoadResource(assetAttr.getResourceDescAsset());
        }
        // Nada que preparar
    }

    public void prepareResourceDescAssetToLoadResource(ResourceDescAsset resourceDescAsset)
    {
        String location = resourceDescAsset.getLocation(xmlDOMParserContext); // Los assets son para pruebas, no merece la pena perder el tiempo intentando usar un "basePath" para poder especificar paths relativos
        // En assets el location empezará siempre con res/, AssetManager.open() NO admite el uso de ".." o /res . No pasa nada, los assets son para pruebas no es necesario que se comporte igual que en remoto (con HTTP)
        InputStream ims = null;
        byte[] res;
        try
        {
            // AssetManager.open es multihilo, de todas formas va a ser MUY raro que usemos assets junto a remote
            // http://www.netmite.com/android/mydroid/frameworks/base/libs/utils/AssetManager.cpp
            AssetManager assetManager = xmlDOMParserContext.getAssetManager();
            ims = assetManager.open(location);
            res = IOUtil.read(ims);
        }
        catch (IOException ex)
        {
            throw new ItsNatDroidException(ex);
        }
        finally
        {
            if (ims != null) try
            {
                ims.close();
            }
            catch (IOException ex)
            {
                throw new ItsNatDroidException(ex);
            }
        }

        parseResourceDescAsset(resourceDescAsset, res);
    }

    private ParsedResource parseResourceDescAsset(ResourceDescAsset resourceDescAsset, byte[] input)
    {
        String resourceMime = resourceDescAsset.getResourceMime();
        if (MimeUtil.isMIMEResourceXML(resourceMime))
        {
            String markup = StringUtil.toString(input, "UTF-8");
            ParsedResourceXMLDOM resource = parseResourceDescDynamicXML(markup, resourceDescAsset, null, XMLDOMLayoutParser.LayoutType.STANDALONE, xmlDOMParserContext);
            XMLDOM xmlDOM = resource.getXMLDOM();
            if (xmlDOM.getDOMAttrRemoteList() != null)
                throw new ItsNatDroidException("Remote resources cannot be specified by a resource loaded as asset");
            return resource;
        }
        else if (MimeUtil.isMIMEResourceImage(resourceMime))
        {
            ParsedResourceImage resource = parseResourceDescDynamicResourceImage(resourceDescAsset, input);
            return resource;
        }
        else throw new ItsNatDroidException("Unsupported resource mime: " + resourceMime);
    }


    public static ParsedResource parseResourceDescRemote(ResourceDescRemote resourceDescRemote, HttpRequestResultOKImpl resultRes, XMLDOMParserContext xmlDOMParserContext) throws Exception
    {
        // Método llamado en multihilo

        // No te preocupes, quizás lo hemos pre-loaded y cacheado via otro DOMAttrRemote idéntico en datos pero diferente instancia, tal es el caso del
        // pre-parseo de código beanshell, es normal que no esté en este DOMAttrRemote obtenido

        String resourceMime = resourceDescRemote.getResourceMime();
        if (MimeUtil.isMIMEResourceXML(resourceMime))
        {
            String markup = resultRes.getResponseText();

            String itsNatServerVersion = resultRes.getItsNatServerVersion(); // Puede ser null

            ParsedResourceXMLDOM resource = parseResourceDescDynamicXML(markup, resourceDescRemote, itsNatServerVersion, XMLDOMLayoutParser.LayoutType.PAGE, xmlDOMParserContext);
            return resource;
        }
        else if (MimeUtil.isMIMEResourceImage(resourceMime))
        {
            byte[] img = resultRes.getResponseByteArray();
            ParsedResourceImage resource = parseResourceDescDynamicResourceImage(resourceDescRemote, img);
            return resource;
        }
        else throw new ItsNatDroidException("Unsupported resource mime: " + resourceMime);
    }


    private static ParsedResourceXMLDOM parseResourceDescDynamicXML(String markup,ResourceDescDynamic resourceDesc, String itsNatServerVersion, XMLDOMLayoutParser.LayoutType layoutType, XMLDOMParserContext xmlDOMParserContext)
    {
        XMLDOMRegistry xmlDOMRegistry = xmlDOMParserContext.getXMLDOMRegistry();

        // Es llamado en multihilo en el caso de DOMAttrRemote
        String resourceType = resourceDesc.getResourceType();

        XMLDOM xmlDOM;
        if (resourceDesc.getValuesResourceName() == null) // No es <drawable> o un <item name="..." type="layout"> <item ... type="anim"> o <item ... type="animator"> en un res/values/archivo.xml
        {
            if (XMLDOMValues.TYPE_ANIM.equals(resourceType))
            {
                throw new ItsNatDroidException("TO DO");
                //xmlDOM = xmlDOMRegistry.getXMLDOMAnimCacheByMarkup(markup, xmlDOMParserContext);
            }
            if (XMLDOMValues.TYPE_ANIMATOR.equals(resourceType))
            {
                xmlDOM = xmlDOMRegistry.getXMLDOMAnimatorCacheByMarkup(markup, resourceDesc, xmlDOMParserContext);
            }
            else if (XMLDOMValues.TYPE_DRAWABLE.equals(resourceType))
            {
                xmlDOM = xmlDOMRegistry.getXMLDOMDrawableCacheByMarkup(markup, xmlDOMParserContext);
            }
            else if (XMLDOMValues.TYPE_LAYOUT.equals(resourceType))
            {
                xmlDOM = xmlDOMRegistry.getXMLDOMLayoutCacheByMarkup(markup, itsNatServerVersion, layoutType, xmlDOMParserContext);
            }
            else throw new ItsNatDroidException("Unsupported resource type as asset or remote: " + resourceType + " or missing ending :selector");
        }
        else if (XMLDOMValues.isResourceTypeValues(resourceType)) // Incluye el caso de <drawable>, <item name="..." type="layout"> <item ... type="anim"> y <item ... type="animator">
        {
            // En el caso "drawable" podemos tener un acceso a un <drawable> en archivo XML en /res/values o bien directamente acceder al XML en /res/drawable
            // Este es el caso de acceso a un item <drawable> de un XML values
            // Idem con <item name="..." type="layout">

            xmlDOM = xmlDOMRegistry.getXMLDOMValuesCacheByMarkup(markup, xmlDOMParserContext);
        }
        else throw new ItsNatDroidException("Unsupported resource type as asset or remote: " + resourceType);

        ParsedResourceXMLDOM resource = new ParsedResourceXMLDOM(xmlDOM);
        resourceDesc.setParsedResource(resource);

        return resource;
    }

    public static ParsedResourceImage parseResourceDescDynamicResourceImage(ResourceDescDynamic resourceDesc, byte[] img)
    {
        ParsedResourceImage resource = new ParsedResourceImage(img);
        resourceDesc.setParsedResource(resource);
        return resource;
    }

    protected abstract DOMElement createElement(String name,DOMElement parent);

}
