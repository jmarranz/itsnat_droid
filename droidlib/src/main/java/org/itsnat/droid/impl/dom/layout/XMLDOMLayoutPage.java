package org.itsnat.droid.impl.dom.layout;

import android.content.res.Configuration;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.XMLDOM;
import org.itsnat.droid.impl.domparser.XMLDOMParserContext;
import org.itsnat.droid.impl.util.NamespaceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmarranz on 19/01/2016.
 */
public abstract class XMLDOMLayoutPage extends XMLDOMLayout
{
    protected ArrayList<DOMScript> scriptList;

    public List<DOMScript> getDOMScriptList()
    {
        return scriptList;
    }

    public void addDOMScript(DOMScript script)
    {
        if (scriptList == null) this.scriptList = new ArrayList<DOMScript>();
        scriptList.add(script);
    }

    @Override
    public void partialClone(XMLDOM cloned)
    {
        super.partialClone(cloned);
        ((XMLDOMLayoutPage)cloned).scriptList = this.scriptList;
    }

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

    public DOMAttr toDOMAttrNotSyncResource(String namespaceURI,String name,String value,XMLDOMParserContext xmlDOMParserContext)
    {
        String namespaceURIFinal = extractAttrNamespaceURI(namespaceURI, name);
        String localName = extractAttrLocalName(namespaceURI, name);
        DOMAttr attr = DOMAttr.create(namespaceURIFinal, localName, value, xmlDOMParserContext);
        return attr;
    }

}
