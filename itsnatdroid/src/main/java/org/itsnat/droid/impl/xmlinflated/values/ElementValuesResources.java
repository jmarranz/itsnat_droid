package org.itsnat.droid.impl.xmlinflated.values;

import android.animation.Animator;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.view.animation.Animation;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.Dimension;
import org.itsnat.droid.impl.xmlinflater.PercFloat;
import org.itsnat.droid.impl.xmlinflater.XMLInflater;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.layout.LayoutValue;
import org.itsnat.droid.impl.xmlinflater.layout.XMLInflaterLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_ANIM;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_ANIMATOR;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_BOOL;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_COLOR;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_DIMEN;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_DRAWABLE;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_FLOAT;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_ID;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_INTEGER;
import static org.itsnat.droid.impl.dom.values.XMLDOMValues.TYPE_LAYOUT;
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

    public DOMAttr findElementValuesChildNoChildElemValue(String type,String name)
    {
        String key = genKey(type,name);
        ElementValuesChildNoChildElem child = (ElementValuesChildNoChildElem)elemValuesElemMap.get(key);
        if (child == null)
            return null;
        return child.getValueAsDOMAttr();
    }

    public DOMAttr getElementValuesChildNoChildElemValue(String type,String name)
    {
        DOMAttr attr = findElementValuesChildNoChildElemValue(type,name);
        if (attr == null)
            throw new ItsNatDroidException("Not found item of type: " + type + " and name: " + name);
        return attr;
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

    public int getIdentifier(String name, String type, XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_ID,name);
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflater.getInflatedXML().getXMLInflaterRegistry();
        return xmlInflaterRegistry.getIdentifier(valueAsDOMAttr.getResourceDesc(), type, xmlInflater);
    }

    public Animation getAnimation(String name,XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_ANIM,name);
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflater.getInflatedXML().getXMLInflaterRegistry();
        return xmlInflaterRegistry.getAnimation(valueAsDOMAttr.getResourceDesc(), xmlInflater);
    }

    public Animator getAnimator(String name,XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_ANIMATOR,name);
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflater.getInflatedXML().getXMLInflaterRegistry();
        return xmlInflaterRegistry.getAnimator(valueAsDOMAttr.getResourceDesc(), xmlInflater);
    }

    public boolean getBoolean(String name,XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_BOOL,name);
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflater.getInflatedXML().getXMLInflaterRegistry();
        return xmlInflaterRegistry.getBoolean(valueAsDOMAttr.getResourceDesc(), xmlInflater);
    }

    public int getColor(String name,XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_COLOR,name);
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflater.getInflatedXML().getXMLInflaterRegistry();
        return xmlInflaterRegistry.getColor(valueAsDOMAttr.getResourceDesc(), xmlInflater);
    }

    public Dimension getDimensionObject(String name, XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_DIMEN,name);
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflater.getInflatedXML().getXMLInflaterRegistry();
        return xmlInflaterRegistry.getDimensionObject(valueAsDOMAttr.getResourceDesc(), xmlInflater);
    }

    public PercFloat getDimensionPercFloat(String name, XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_DIMEN,name);
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflater.getInflatedXML().getXMLInflaterRegistry();
        return xmlInflaterRegistry.getDimensionPercFloat(valueAsDOMAttr.getResourceDesc(), xmlInflater);
    }

    public Drawable getDrawable(String name, XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_DRAWABLE,name);
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflater.getInflatedXML().getXMLInflaterRegistry();
        return xmlInflaterRegistry.getDrawable(valueAsDOMAttr.getResourceDesc(), xmlInflater);
    }

    public LayoutValue getLayout(String name, XMLInflaterLayout xmlInflater, ViewGroup viewParent, int indexChild, ArrayList<DOMAttr> includeAttribs)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_LAYOUT,name);
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflater.getInflatedXML().getXMLInflaterRegistry();
        return xmlInflaterRegistry.getLayout(valueAsDOMAttr.getResourceDesc(), xmlInflater, viewParent, indexChild, includeAttribs);
    }

    public float getFloat(String name,XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_FLOAT,name);
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflater.getInflatedXML().getXMLInflaterRegistry();
        return xmlInflaterRegistry.getFloat(valueAsDOMAttr.getResourceDesc(), xmlInflater);
    }

    public int getInteger(String name,XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_INTEGER,name);
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflater.getInflatedXML().getXMLInflaterRegistry();
        return xmlInflaterRegistry.getInteger(valueAsDOMAttr.getResourceDesc(), xmlInflater);
    }

    public String getString(String name,XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_STRING,name);
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflater.getInflatedXML().getXMLInflaterRegistry();
        return xmlInflaterRegistry.getString(valueAsDOMAttr.getResourceDesc(), xmlInflater);
    }

    public CharSequence getText(String name,XMLInflater xmlInflater)
    {
        DOMAttr valueAsDOMAttr = getElementValuesChildNoChildElemValue(TYPE_STRING,name); // NO HAY tipo "text" tal y como <text name="somename">some <b>text</b></text>
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflater.getInflatedXML().getXMLInflaterRegistry();
        return xmlInflaterRegistry.getText(valueAsDOMAttr.getResourceDesc(), xmlInflater);
    }

    public CharSequence[] getTextArray(String name,XMLInflater xmlInflater)
    {
        List<DOMAttr> valueAsDOMAttrList = getElementValuesChildWithChildElemValue(TYPE_STRING_ARRAY, name); // NO HAY tipo "text-array" nos basamos en "string-array" los items pueden ser HTML y resolverse con getText
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflater.getInflatedXML().getXMLInflaterRegistry();
        CharSequence[] res = new CharSequence[valueAsDOMAttrList.size()];
        int i = 0;
        for(DOMAttr valueAsDOMAttr : valueAsDOMAttrList)
        {
            res[i] = xmlInflaterRegistry.getText(valueAsDOMAttr.getResourceDesc(), xmlInflater);
            i++;
        }
        return res;
    }

    public float getPercent(String name,XMLInflater xmlInflater)
    {
        // Puede usarse un <string...>10.5%</string> o un <item ... type="dimen">10.5%</item>
        DOMAttr valueAsDOMAttr = findElementValuesChildNoChildElemValue(TYPE_STRING, name);
        if (valueAsDOMAttr == null) valueAsDOMAttr = findElementValuesChildNoChildElemValue(TYPE_DIMEN, name);
        if (valueAsDOMAttr == null) throw new ItsNatDroidException("Expected a <string> or <item type=\"dimen\"> for resource name: " + name);
        XMLInflaterRegistry xmlInflaterRegistry = xmlInflater.getInflatedXML().getXMLInflaterRegistry();
        return xmlInflaterRegistry.getPercent(valueAsDOMAttr.getResourceDesc(), xmlInflater);
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
