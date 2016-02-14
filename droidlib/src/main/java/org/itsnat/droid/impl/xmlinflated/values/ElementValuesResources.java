package org.itsnat.droid.impl.xmlinflated.values;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.Dimension;
import org.itsnat.droid.impl.xmlinflater.PercFloat;
import org.itsnat.droid.impl.xmlinflater.XMLInflateRegistry;
import org.itsnat.droid.impl.xmlinflater.XMLInflater;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_COLOR;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_BOOL;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_DIMEN;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_STRING;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_STRING_ARRAY;

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
        else if (child instanceof ElementValuesArrayBase) // <string-array>,<integer-array>,<array>
        {
            ElementValuesArrayBase childArrayBase = (ElementValuesArrayBase)child;
            String key = genKey(childArrayBase.getTagName(), childArrayBase.getName());
            old = elemValuesElemMap.put(key,childArrayBase);
            if (old != null) throw new ItsNatDroidException("Already exists an element " + childArrayBase.getTagName() + " with name " + childArrayBase.getName());
        }
        else throw new ItsNatDroidException("Not supported in values folder the element : " + child.getTagName());
    }

    public DOMAttr getElementValuesChildNoChildElemValue(String type,String name)
    {
        String key = genKey(type,name);
        ElementValuesChildNoChildElem child = (ElementValuesChildNoChildElem)elemValuesElemMap.get(key);
        if (child == null)
            throw new ItsNatDroidException("Not found item of type: " + type + " and name: " + name);
        return child.getValueAsDOMAttr();
    }

    public List<DOMAttr> getElementValuesChildWithChildElemValue(String type,String name)
    {
        String key = genKey(type,name);
        ElementValuesChildWithChildElem child = (ElementValuesChildWithChildElem)elemValuesElemMap.get(key);
        if (child == null)
            throw new ItsNatDroidException("Not found item of type: " + type + " and name: " + name);
        return child.getChildDOMAttrValueList();
    }


    private static String genKey(String type,String name)
    {
        return type + "#" + name;
    }

    public boolean getBoolean(String name,XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_BOOL,name);
        XMLInflateRegistry xmlInflateRegistry = xmlInflater.getInflatedXML().getXMLInflateRegistry();
        return xmlInflateRegistry.getBoolean(valueAsDOMAttr, xmlInflater);
    }

    public int getColor(String name,XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_COLOR,name);
        XMLInflateRegistry xmlInflateRegistry = xmlInflater.getInflatedXML().getXMLInflateRegistry();
        return xmlInflateRegistry.getColor(valueAsDOMAttr, xmlInflater);
    }

    public Dimension getDimensionObject(String name, XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_DIMEN,name);
        XMLInflateRegistry xmlInflateRegistry = xmlInflater.getInflatedXML().getXMLInflateRegistry();
        return xmlInflateRegistry.getDimensionObject(valueAsDOMAttr, xmlInflater);
    }

    public PercFloat getDimensionPercFloat(String name, XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_DIMEN,name);
        XMLInflateRegistry xmlInflateRegistry = xmlInflater.getInflatedXML().getXMLInflateRegistry();
        return xmlInflateRegistry.getDimensionPercFloat(valueAsDOMAttr, xmlInflater);
    }

    public String getString(String name,XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_STRING,name);
        XMLInflateRegistry xmlInflateRegistry = xmlInflater.getInflatedXML().getXMLInflateRegistry();
        return xmlInflateRegistry.getString(valueAsDOMAttr, xmlInflater);
    }

    public CharSequence getText(String name,XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_STRING,name); // NO HAY tipo "text" tal y como <text name="somename">some <b>text</b></text>
        XMLInflateRegistry xmlInflateRegistry = xmlInflater.getInflatedXML().getXMLInflateRegistry();
        return xmlInflateRegistry.getText(valueAsDOMAttr, xmlInflater);
    }

    public CharSequence[] getTextArray(String name,XMLInflater xmlInflater)
    {
        List<DOMAttr> valueAsDOMAttrList = getElementValuesChildWithChildElemValue(TYPE_STRING_ARRAY, name); // NO HAY tipo "text-array" nos basamos en "string-array" los items pueden ser HTML y resolverse con getText
        XMLInflateRegistry xmlInflateRegistry = xmlInflater.getInflatedXML().getXMLInflateRegistry();
        CharSequence[] res = new CharSequence[valueAsDOMAttrList.size()];
        int i = 0;
        for(DOMAttr valueAsDOMAttr : valueAsDOMAttrList)
        {
            res[i] = xmlInflateRegistry.getText(valueAsDOMAttr, xmlInflater);
            i++;
        }
        return res;
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
