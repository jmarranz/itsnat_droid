package org.itsnat.droid.impl.xmlinflated.values;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.Dimension;
import org.itsnat.droid.impl.xmlinflater.PercFloat;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.XMLInflater;

import java.util.HashMap;
import java.util.Map;

/**
 * Es la clase asociada al tag root "resources"
 * Created by jmarranz on 27/11/14.
 */
public class ElementValuesResources extends ElementValues
{
    private Map<String,ElementValuesChild> elemValuesElemMap = new HashMap<String,ElementValuesChild>();


    public ElementValuesResources()
    {
        super("resources");
    }

    @Override
    public ElementValuesResources getRootElementResources()
    {
        return this;
    }

    @Override
    public void addChildElementValues(ElementValues child)
    {
        super.addChildElementValues(child);

        // Es posible repetir en dos items el name, incluso style puede tener el name de un <dimen>, lo importante es que NO coincida exactamente el mismo tipo y nombre

        ElementValuesChild old;
        if (child instanceof ElementValuesItemNormal)
        {
            ElementValuesItemNormal childItemNormal = (ElementValuesItemNormal)child;
            String key = genKey(childItemNormal.getType(), childItemNormal.getName());
            old = elemValuesElemMap.put(key,childItemNormal);
            if (old != null) throw new ItsNatDroidException("Already exists an element with type " + childItemNormal.getType() + " and name " + childItemNormal.getName());
        }
        else if (child instanceof ElementValuesStyle)
        {
            ElementValuesStyle childStyle = (ElementValuesStyle)child;
            String key = genKey("style",childStyle.getName());
            old = elemValuesElemMap.put(key,childStyle);
            if (old != null) throw new ItsNatDroidException("Already exists an element with type style and name " + childStyle.getName());
        }
        else throw new ItsNatDroidException("Not supported in values folder the element : " + child.getElementName());


    }

    public DOMAttr getElementValuesChildNoChildElemValue(String type,String name)
    {
        String key = genKey(type,name);
        ElementValuesChildNoChildElem child = (ElementValuesChildNoChildElem)elemValuesElemMap.get(key);
        if (child == null)
            throw new ItsNatDroidException("Not found item of type: " + type + " and name: " + name);
        return child.getValueAsDOMAttr();
    }

    private static String genKey(String type,String name)
    {
        return type + "#" + name;
    }

    public int getColor(String name,XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue("color",name);
        XMLInflateRegistry xmlInflateRegistry = xmlInflater.getInflatedXML().getXMLInflateRegistry();
        return xmlInflateRegistry.getColor(valueAsDOMAttr, xmlInflater);
    }


    public Dimension getDimensionObject(String name, XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue("dimen",name);
        XMLInflateRegistry xmlInflateRegistry = xmlInflater.getInflatedXML().getXMLInflateRegistry();
        return xmlInflateRegistry.getDimensionObject(valueAsDOMAttr, xmlInflater);
    }

    public PercFloat getDimensionPercFloat(String name, XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue("dimen",name);
        XMLInflateRegistry xmlInflateRegistry = xmlInflater.getInflatedXML().getXMLInflateRegistry();
        return xmlInflateRegistry.getDimensionPercFloat(valueAsDOMAttr, xmlInflater);
    }

    public ElementValuesStyle getViewStyle(String name)
    {
        String key = genKey("style",name);
        ElementValuesStyle child = (ElementValuesStyle)elemValuesElemMap.get(key);
        if (child == null)
            throw new ItsNatDroidException("Not found style item with name: " + name);
        return child;
    }

}
