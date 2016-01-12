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
    private Map<String,ElementValuesChildNoChildElem> elemValuesNoChildElemMap = new HashMap<String,ElementValuesChildNoChildElem>();

    public ElementValuesResources()
    {
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
        if (child instanceof ElementValuesChildNoChildElem)
        {
            ElementValuesChildNoChildElem childNoChildElem = (ElementValuesChildNoChildElem)child;
            String key = genKey(childNoChildElem.getType(),childNoChildElem.getName());
            elemValuesNoChildElemMap.put(key,childNoChildElem);
        }
    }

    public String getElementValuesChildNoChildElemValue(String type,String name)
    {
        String key = genKey(type,name);
        ElementValuesChildNoChildElem child = elemValuesNoChildElemMap.get(key);
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
        return null;
    }
}
