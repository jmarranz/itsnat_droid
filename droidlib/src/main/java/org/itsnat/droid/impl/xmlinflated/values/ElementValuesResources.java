package org.itsnat.droid.impl.xmlinflated.values;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;

import java.util.HashMap;
import java.util.List;
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

        if (child instanceof ElementValuesChildNoChildElem)
        {
            ElementValuesChildNoChildElem childNoChildElem = (ElementValuesChildNoChildElem)child;
            String key = genKey(childNoChildElem.getType(),childNoChildElem.getName());
            elemValuesElemMap.put(key,childNoChildElem);
        }
        else if (child instanceof ElementValuesChildStyle)
        {
            ElementValuesChildStyle childStyle = (ElementValuesChildStyle)child;
            String key = genKey("style",childStyle.getName());
            elemValuesElemMap.put(key,childStyle);
        }
        else throw new ItsNatDroidException("Not supported in values folder the element : " + child.getElementName());
    }

    public String getElementValuesChildNoChildElemValue(String type,String name)
    {
        String key = genKey(type,name);
        ElementValuesChildNoChildElem child = (ElementValuesChildNoChildElem)elemValuesElemMap.get(key);
        if (child == null)
            throw new ItsNatDroidException("Not found item of type: " + type + " and name: " + name);
        return child.getValue();
    }

    private static String genKey(String type,String name)
    {
        return type + "#" + name;
    }

    public String getColor(String name)
    {
        return getElementValuesChildNoChildElemValue("color",name);
    }

    public String getDimension(String name)
    {
        return getElementValuesChildNoChildElemValue("dimen",name);
    }

    public List<DOMAttr> getViewStyle(String name)
    {
        String key = genKey("style",name);
        ElementValuesChildStyle child = (ElementValuesChildStyle)elemValuesElemMap.get(key);
        if (child == null)
            throw new ItsNatDroidException("Not found style item with name: " + name);
        return child.getDOMAttrList();
    }
}
