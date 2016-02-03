package org.itsnat.droid.impl.dom;

import org.itsnat.droid.impl.util.MiscUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jmarranz on 03/02/2016.
 */
public class DOMAttributeMap
{
    protected LinkedHashMap<String,DOMAttr> attribMapList;

    public DOMAttributeMap()
    {
    }

    public Map<String,DOMAttr> getDOMAttributes()
    {
        return attribMapList; // Puede ser null
    }

    public boolean hasAttributes()
    {
        return (attribMapList != null && !attribMapList.isEmpty());
    }

    public void initDOMAttribMap(int count) // Método opcional para optimizar memoria si se conoce el número exacto de atributos
    {
        this.attribMapList = new LinkedHashMap<String,DOMAttr>(count);
    }

    public void setDOMAttribute(DOMAttr attr)
    {
        if (attribMapList == null) this.attribMapList = new LinkedHashMap<String,DOMAttr>();

        String namespaceURI = attr.getNamespaceURI();
        String name = attr.getName(); // El nombre devuelto no contiene el namespace
        String key = toKey(namespaceURI, name);
        attribMapList.put(key,attr);
    }

    public DOMAttr getDOMAttribute(String namespaceURI, String name)
    {
        if (attribMapList == null)
            return null;

        String key = toKey(namespaceURI, name);
        return attribMapList.get(key);
    }

    public void removeDOMAttribute(String namespaceURI,String name) // No se llama nunca pero lo dejamos por coherencia
    {
        if (attribMapList == null) return;
        String key = toKey(namespaceURI, name);
        attribMapList.remove(key);
    }

    private static String toKey(String namespaceURI, String name)
    {
        return MiscUtil.isEmpty(namespaceURI) ? name : (namespaceURI + ":" + name);
    }
}
