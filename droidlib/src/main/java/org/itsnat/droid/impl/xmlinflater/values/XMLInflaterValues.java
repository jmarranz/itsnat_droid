package org.itsnat.droid.impl.xmlinflater.values;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.values.DOMElemValues;
import org.itsnat.droid.impl.dom.values.XMLDOMValues;
import org.itsnat.droid.impl.xmlinflated.values.ElementValues;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesChild;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesResources;
import org.itsnat.droid.impl.xmlinflated.values.InflatedValues;
import org.itsnat.droid.impl.xmlinflated.values.InflatedValuesPage;
import org.itsnat.droid.impl.xmlinflated.values.InflatedValuesStandalone;
import org.itsnat.droid.impl.xmlinflater.XMLInflater;
import org.itsnat.droid.impl.xmlinflater.values.classtree.ClassDescValues;

import java.util.LinkedList;

/**
 * Created by jmarranz on 4/11/14.
 */
public abstract class XMLInflaterValues extends XMLInflater
{
    protected XMLInflaterValues(InflatedValues inflatedXML,int bitmapDensityReference, AttrLayoutInflaterListener attrLayoutInflaterListener, AttrDrawableInflaterListener attrDrawableInflaterListener)
    {
        super(inflatedXML, bitmapDensityReference, attrLayoutInflaterListener, attrDrawableInflaterListener);
    }

    public static XMLInflaterValues createXMLInflaterValues(InflatedValues inflatedValues,int bitmapDensityReference,AttrLayoutInflaterListener attrLayoutInflaterListener,AttrDrawableInflaterListener attrDrawableInflaterListener)
    {
        if (inflatedValues instanceof InflatedValuesPage)
        {
            return new XMLInflaterValuesPage((InflatedValuesPage)inflatedValues,bitmapDensityReference,attrLayoutInflaterListener,attrDrawableInflaterListener);
        }
        else if (inflatedValues instanceof InflatedValuesStandalone)
        {
            return new XMLInflaterValuesStandalone((InflatedValuesStandalone)inflatedValues,bitmapDensityReference,attrLayoutInflaterListener,attrDrawableInflaterListener);
        }
        return null; // Internal Error
    }


    public InflatedValues getInflatedValues()
    {
        return (InflatedValues)inflatedXML;
    }

    public ElementValuesResources inflateValues()
    {
        return inflateRoot(getInflatedValues().getXMLDOMValues());
    }

    private ElementValuesResources inflateRoot(XMLDOMValues xmlDOMValues)
    {
        DOMElemValues rootDOMElem = (DOMElemValues)xmlDOMValues.getRootDOMElement();

        ElementValuesResources elementResources = new ElementValuesResources();
        getInflatedValues().setRootElementResources(elementResources);

        processChildElements(rootDOMElem, elementResources);

        return elementResources;
    }


    public void processChildElements(DOMElemValues domElemParent,ElementValues parentChildValues)
    {
        LinkedList<DOMElement> childDOMElemList = domElemParent.getChildDOMElementList();
        if (childDOMElemList == null) return;

        parentChildValues.initChildElementValuesList(childDOMElemList.size());
        for (DOMElement childDOMElem : childDOMElemList)
        {
            ElementValues childElemValues = inflateNextElement((DOMElemValues)childDOMElem,domElemParent,parentChildValues);
            parentChildValues.addChildElementValues(childElemValues);
        }
    }

    protected ElementValues inflateNextElement(DOMElemValues domElement,DOMElemValues domElementParent,ElementValues parentChildValues)
    {
        ElementValues childElem = createElementValuesChildNoChildren(domElement, parentChildValues);
        // No procesamos los child elements porque cada ElementValues sabe como gestionar sus hijos si es que los tiene
        return childElem;
    }

    private ElementValues createElementValuesChildNoChildren(DOMElemValues domElement,ElementValues parentChildValues)
    {
        String resourceType = getResourceType(domElement);

        ClassDescValuesMgr classDescValuesMgr = getInflatedValues().getXMLInflateRegistry().getClassDescValuesMgr();
        ClassDescValues classDesc = classDescValuesMgr.get(resourceType);
        if (classDesc == null)
            throw new ItsNatDroidException("Not found processor for resource type: " + resourceType);

        ElementValuesChild childValuesChild = classDesc.createElementValuesChildNoChildren(domElement, parentChildValues);
        return childValuesChild;
    }


    private String getResourceType(DOMElemValues domElement)
    {
        // Sólo vale para los elementos directamente por debajo de <resources> no para los hijos de <style> cuyos <item> no tienen atributo "type"
        String elemName = domElement.getName();
        if (elemName.equals("item"))
        {
            DOMAttr attrType = domElement.findDOMAttribute(null, "type");
            if (attrType == null)
            {
                DOMAttr attrName = domElement.findDOMAttribute(null, "name");
                if (attrName == null)
                    throw new ItsNatDroidException("Missing attribute name in <item>");

                throw new ItsNatDroidException("Missing attribute type in <item> with name: " + attrName.getValue());
            }
            return attrType.getValue();
        }
        else
        {
            return elemName; // Ej <dimen> <color> etc
        }
    }

}
