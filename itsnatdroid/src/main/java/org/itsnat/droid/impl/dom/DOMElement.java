package org.itsnat.droid.impl.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jmarranz on 31/10/14.
 */
public abstract class DOMElement
{
    protected String tagName;
    protected DOMElement parentElement; // Si es null es el root
    protected DOMAttributeMap attribs = new DOMAttributeMap();
    protected List<DOMElement> childList;

    public DOMElement(String tagName,DOMElement parentElement)
    {
        this.tagName = tagName;
        this.parentElement = parentElement;
    }

    public DOMElement(DOMElement toCopy)
    {
        this.tagName = toCopy.tagName;
        this.parentElement = toCopy.parentElement;
        this.attribs = toCopy.attribs;
        this.childList = toCopy.childList;
    }


    public String getTagName()
    {
        return tagName;
    }

    public void setTagName(String tagName)
    {
        this.tagName = tagName;
    }

    public DOMElement getParentDOMElement()
    {
        return parentElement;
    }

    public void initDOMAttribMap(int count)
    {
        this.attribs.initDOMAttribMap(count);
    }

    public DOMAttributeMap getDOMAttributeMap()
    {
        return attribs;
    }

    public Map<String,DOMAttr> getDOMAttributes()
    {
        return attribs.getDOMAttributes(); // Puede ser null
    }

    public DOMAttr getDOMAttribute(String namespaceURI, String name)
    {
        return attribs.getDOMAttribute(namespaceURI, name);
    }

    public void setDOMAttribute(DOMAttr attr)
    {
        attribs.setDOMAttribute(attr);
    }

    /*
    public void removeDOMAttribute(String namespaceURI, String name) // No se llama nunca pero lo dejamos por coherencia
    {
        attribs.removeDOMAttribute(namespaceURI, name);
    }
    */


    public List<DOMElement> getChildDOMElementList()
    {
        return childList;
    }


    public void addChildDOMElement(DOMElement domElement)
    {
        if (childList == null) this.childList = new ArrayList<DOMElement>();
        childList.add(domElement);
    }

}
