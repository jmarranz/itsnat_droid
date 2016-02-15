package org.itsnat.droid.impl.domparser;

import android.content.res.AssetManager;
import android.util.Xml;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.HttpRequestResultOKImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMAttrAsset;
import org.itsnat.droid.impl.dom.DOMAttrDynamic;
import org.itsnat.droid.impl.dom.DOMAttrRemote;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.ParsedResource;
import org.itsnat.droid.impl.dom.ParsedResourceImage;
import org.itsnat.droid.impl.dom.ParsedResourceXMLDOM;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.domparser.layout.XMLDOMLayoutParser;
import org.itsnat.droid.impl.domparser.values.XMLDOMValuesParser;
import org.itsnat.droid.impl.util.IOUtil;
import org.itsnat.droid.impl.util.MimeUtil;
import org.itsnat.droid.impl.util.MiscUtil;
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
    protected XMLDOMRegistry xmlDOMRegistry;
    protected AssetManager assetManager;

    public XMLDOMParser(XMLDOMRegistry xmlDOMRegistry,AssetManager assetManager)
    {
        this.xmlDOMRegistry = xmlDOMRegistry;
        this.assetManager = assetManager;
    }

    public static XmlPullParser newPullParser(Reader input)
    {
        try
        {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            parser.setInput(input);
            return parser;
        }
        catch (XmlPullParserException ex) { throw new ItsNatDroidException(ex); }
    }

    protected void setRootElement(DOMElement rootElement, XMLDOM xmlDOM)
    {
        xmlDOM.setRootElement(rootElement);
    }

    public DOMElement parseRootElement(String rootElemName,XmlPullParser parser,XMLDOM xmlDOM) throws IOException, XmlPullParserException
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
            throw new ItsNatDroidException("Missing android namespace declaration in root element");

        DOMElement rootElement = createRootElementAndFillAttributes(rootElemName, parser, xmlDOM);

        processChildElements(rootElement, parser, xmlDOM);

        setRootElement(rootElement, xmlDOM);

        return rootElement;
    }

    protected boolean isAndroidNSPrefixNeeded()
    {
        return true; // Se redefine
    }

    protected DOMElement createRootElementAndFillAttributes(String name,XmlPullParser parser,XMLDOM xmlDOM) throws IOException, XmlPullParserException
    {
        DOMElement rootElement = createElement(name, null);

        fillAttributesAndAddElement(null, rootElement, parser, xmlDOM);

        return rootElement;
    }

    protected DOMElement createElementAndFillAttributesAndAdd(String name, DOMElement parentElement, XmlPullParser parser,XMLDOM xmlDOM)
    {
        // parentElementDrawable es null en el caso de parseo de fragment
        DOMElement element = createElement(name,parentElement);

        fillAttributesAndAddElement(parentElement, element, parser, xmlDOM);

        return element;
    }

    protected void fillAttributesAndAddElement(DOMElement parentElement, DOMElement element,XmlPullParser parser,XMLDOM xmlDOM)
    {
        fillElementAttributes(element, parser, xmlDOM);
        if (parentElement != null) parentElement.addChildDOMElement(element);
    }

    protected void fillElementAttributes(DOMElement element,XmlPullParser parser,XMLDOM xmlDOM)
    {
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

    protected void addDOMAttr(DOMElement element, DOMAttr attrib, XMLDOM xmlDOMParent)
    {
        prepareDOMAttrToDownload(attrib,xmlDOMParent);
        element.setDOMAttribute(attrib);
    }

    protected DOMAttr addDOMAttr(DOMElement element, String namespaceURI, String name, String value, XMLDOM xmlDOMParent)
    {
        DOMAttr attrib = DOMAttr.create(namespaceURI, name, value);
        addDOMAttr(element,attrib,xmlDOMParent);
        return attrib;
    }

    private void prepareDOMAttrToDownload(DOMAttr attrib, XMLDOM xmlDOMParent)
    {
        if (attrib instanceof DOMAttrRemote)
        {
            xmlDOMParent.addDOMAttrRemote((DOMAttrRemote) attrib);
        }
        else if (attrib instanceof DOMAttrAsset)
        {
            DOMAttrAsset assetAttr = (DOMAttrAsset)attrib;

            String location = assetAttr.getLocation(); // Los assets son para pruebas, no merece la pena perder el tiempo intentando usar un "basePath" para poder especificar paths relativos
            InputStream ims = null;
            byte[] res;
            try
            {
                // AssetManager.open es multihilo, de todas formas va a ser MUY raro que usemos assets junto a remote
                // http://www.netmite.com/android/mydroid/frameworks/base/libs/utils/AssetManager.cpp
                ims = assetManager.open(location);
                res = IOUtil.read(ims);
            }
            catch (IOException ex)
            {
                throw new ItsNatDroidException(ex);
            }
            finally
            {
                if (ims != null) try { ims.close(); } catch (IOException ex) { throw new ItsNatDroidException(ex); }
            }

            parseDOMAttrAsset(assetAttr, res);
        }
    }


    private Object parseDOMAttrAsset(DOMAttrAsset assetAttr, byte[] input)
    {
        String resourceMime = assetAttr.getResourceMime();
        if (MimeUtil.isMIMEResourceXML(resourceMime))
        {
            String markup = MiscUtil.toString(input, "UTF-8");
            ParsedResourceXMLDOM resource = parseDOMAttrDynamicXML(assetAttr, markup, null, XMLDOMLayoutParser.LayoutType.STANDALONE, xmlDOMRegistry, assetManager);
            XMLDOM xmlDOM = resource.getXMLDOM();
            if (xmlDOM.getDOMAttrRemoteList() != null)
                throw new ItsNatDroidException("Remote resources cannot be specified by a resource loaded as asset");
            return xmlDOM;
        }
        else if (MimeUtil.isMIMEResourceImage(resourceMime))
        {
            assetAttr.setResource(new ParsedResourceImage(input));
            return input;
        }
        else throw new ItsNatDroidException("Unsupported resource mime: " + resourceMime);
    }


    public static ParsedResource parseDOMAttrRemote(DOMAttrRemote remoteAttr, HttpRequestResultOKImpl resultRes, XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager) throws Exception
    {
        // Método llamado en multihilo

        // No te preocupes, quizás lo hemos pre-loaded y cacheado via otro DOMAttrRemote idéntico en datos pero diferente instancia, tal es el caso del
        // pre-parseo de código beanshell, es normal que no esté en este DOMAttrRemote obtenido

        String resourceMime = remoteAttr.getResourceMime();
        if (MimeUtil.isMIMEResourceXML(resourceMime))
        {
            String markup = resultRes.getResponseText();

            String itsNatServerVersion = resultRes.getItsNatServerVersion(); // Puede ser null

            ParsedResourceXMLDOM resource = parseDOMAttrDynamicXML(remoteAttr, markup, itsNatServerVersion, XMLDOMLayoutParser.LayoutType.PAGE, xmlDOMRegistry, assetManager);
            return resource;
        }
        else if (MimeUtil.isMIMEResourceImage(resourceMime))
        {
            byte[] img = resultRes.getResponseByteArray();
            ParsedResourceImage resource = new ParsedResourceImage(img);
            remoteAttr.setResource(resource);
            return resource;
        }
        else throw new ItsNatDroidException("Unsupported resource mime: " + resourceMime);
    }


    private static ParsedResourceXMLDOM parseDOMAttrDynamicXML(DOMAttrDynamic attr, String markup, String itsNatServerVersion, XMLDOMLayoutParser.LayoutType layoutType, XMLDOMRegistry xmlDOMRegistry, AssetManager assetManager)
    {
        // Es llamado en multihilo en el caso de DOMAttrRemote
        String resourceType = attr.getResourceType();

        XMLDOM xmlDOM;
        if (attr.getValuesResourceName() == null) // No es <drawable> o un <item name="..." type="layout"> en un res/values/archivo.xml
        {
            if ("drawable".equals(resourceType))
            {
                xmlDOM = xmlDOMRegistry.getXMLDOMDrawableCache(markup, assetManager);
            }
            else if ("layout".equals(resourceType))
            {
                xmlDOM = xmlDOMRegistry.getXMLDOMLayoutCache(markup, itsNatServerVersion, layoutType, assetManager);
            }
            else throw new ItsNatDroidException("Unsupported resource type as asset or remote: " + resourceType + " or missing ending :selector");
        }
        else if (XMLDOMValuesParser.isResourceTypeValues(resourceType)) // Incluye el caso de <drawable> y <item name="..." type="layout">
        {
            xmlDOM = xmlDOMRegistry.getXMLDOMValuesCache(markup, assetManager);
        }
        else throw new ItsNatDroidException("Unsupported resource type as asset or remote: " + resourceType);

        ParsedResourceXMLDOM resource = new ParsedResourceXMLDOM(xmlDOM);
        attr.setResource(resource);

        return resource;
    }

    protected abstract DOMElement createElement(String name,DOMElement parent);

}
