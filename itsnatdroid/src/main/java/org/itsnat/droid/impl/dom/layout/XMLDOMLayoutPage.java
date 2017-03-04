package org.itsnat.droid.impl.dom.layout;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.util.NamespaceUtil;

/**
 * Created by jmarranz on 19/01/2016.
 */
public abstract class XMLDOMLayoutPage extends XMLDOMLayout
{
    public String extractAttrNamespaceURI(String namespaceURI, String name)
    {
        if (namespaceURI != null) return namespaceURI; // De ItsNat server el script nunca contiene un android:localname (localname a secas) si el namespaceURI ha sido especificado explícitamente

        // El namespace puede estar en el name como prefijo por ejemplo "android:"
        String prefix = NamespaceUtil.getPrefix(name);
        if (prefix == null)
            return null;

        return getRootNamespaceByPrefix(prefix); // Puede ser null, no encontrado
    }

    public String extractAttrLocalName(String namespaceURI, String name)
    {
        if (namespaceURI != null) return name; // De ItsNat server el script nunca contiene un android:localname (localname a secas) si el namespaceURI ha sido especificado explícitamente

        // El namespace puede estar en el name como prefijo por ejemplo "android:"
        String prefix = NamespaceUtil.getPrefix(name);
        if (prefix == null)
            return name;

        namespaceURI = getRootNamespaceByPrefix(prefix);
        if (namespaceURI != null) // Sólo se soportan namespaces declarados en el View root, si es null se procesará lo consideramos como un atributo desconocido y conservamos el prefijo
            return NamespaceUtil.getLocalName(name);

        return name;
    }

    public DOMAttr createDOMAttrNotSyncResource(String namespaceURI, String name, String value)
    {
        String namespaceURIFinal = extractAttrNamespaceURI(namespaceURI, name);
        String localName = extractAttrLocalName(namespaceURI, name);
        DOMAttr attr = DOMAttr.createDOMAttr(namespaceURIFinal, localName, value);
        return attr;
    }

}
