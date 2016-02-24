package org.itsnat.droid.impl.dom;

import android.content.res.Configuration;
import android.util.DisplayMetrics;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;

/**
 * Created by jmarranz on 27/10/14.
 */
public abstract class DOMAttr
{
    protected final String namespaceURI;
    protected final String name;
    protected final String value;

    protected DOMAttr(String namespaceURI, String name, String value)
    {
        if ("".equals(namespaceURI)) throw new ItsNatDroidException("Internal error: empty string not allowed"); // Debe ser null o una cadena no vacía
        this.namespaceURI = namespaceURI;
        this.name = name;
        this.value = value;
    }

    public static DOMAttr create(String namespaceURI, String name, String value,XMLDOMParserContext xmlDOMParserContext)
    {
        if (DOMAttrRemote.isRemote(value))
            return new DOMAttrRemote(namespaceURI,name,value,xmlDOMParserContext);
        else if (DOMAttrAsset.isAsset( value))
            return new DOMAttrAsset(namespaceURI,name,value,xmlDOMParserContext);
        else
            return new DOMAttrCompiledResource(namespaceURI,name,value);
    }


    public String getNamespaceURI()
    {
        return namespaceURI;
    }

    public String getName()
    {
        return name;
    }

    public String getValue()
    {
        return value;
    }

}
