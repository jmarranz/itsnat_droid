package org.itsnat.droid.impl.dom;

import org.itsnat.droid.impl.util.MiscUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 31/10/14.
 */
public abstract class DOMElement
{
    protected String name;
    protected DOMElement parentElement; // Si es null es el root
    protected LinkedHashMap<String,DOMAttr> attribMapList;
    protected LinkedList<DOMElement> childList;

    public DOMElement(String name,DOMElement parentElement)
    {
        this.name = name;
        this.parentElement = parentElement;
    }

    public DOMElement(DOMElement toCopy)
    {
        this.name = toCopy.name;
        this.parentElement = toCopy.parentElement;
        this.attribMapList = toCopy.attribMapList;
        this.childList = toCopy.childList;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public DOMElement getParentDOMElement()
    {
        return parentElement;
    }

    public Map<String,DOMAttr> getDOMAttributes()
    {
        return attribMapList; // Puede ser null
    }

    public void initDOMAttribList(int count)
    {
        this.attribMapList = new LinkedHashMap<String,DOMAttr>(count);
    }

    public void addDOMAttribute(DOMAttr attr)
    {
        String key = toKey(attr.getNamespaceURI(),attr.getName());
        attribMapList.put(key,attr);
    }

    public DOMAttr findDOMAttribute(String namespaceURI, String name)
    {
        if (attribMapList == null)
            return null;

        String key = toKey(namespaceURI,name);
        return attribMapList.get(key);
    }

    private static String toKey(String namespaceURI, String name)
    {
        return MiscUtil.isEmpty(namespaceURI) ? name : (namespaceURI + ":" + name);
    }

    public LinkedList<DOMElement> getChildDOMElementList()
    {
        return childList;
    }


    public void addChildDOMElement(DOMElement domElement)
    {
        if (childList == null) this.childList = new LinkedList<DOMElement>();
        childList.add(domElement);
    }

}
