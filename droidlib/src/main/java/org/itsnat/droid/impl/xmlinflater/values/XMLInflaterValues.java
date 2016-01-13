package org.itsnat.droid.impl.xmlinflater.values;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
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
import org.itsnat.droid.impl.xmlinflater.values.classtree.ClassDescValuesChildNoChildElem;
import org.itsnat.droid.impl.xmlinflater.values.classtree.ClassDescValuesChildStyle;

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

        processChildNoChildElemElements(rootDOMElem, elementResources);

        return elementResources;
    }

    private void processChildNoChildElemElements(DOMElemValues domElemParent, ElementValuesResources parentChildValues)
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

    protected ElementValues inflateNextElement(DOMElemValues domElement,DOMElemValues domElementParent,ElementValuesResources parentChildValues)
    {
        ElementValues childElem;
        String elemName = domElement.getName();
        if ("style".equals(elemName))
        {
           childElem = createElementValuesChildStyle(domElement, parentChildValues);
        }
        else
        {
            childElem = createElementValuesChildNoChildren(domElement, parentChildValues);
        }

        // No procesamos los child elements porque cada ElementValues sabe como gestionar sus hijos si es que los tiene
        return childElem;
    }

    private ElementValues createElementValuesChildStyle(DOMElemValues domElement,ElementValuesResources parentChildValues)
    {
        ClassDescValuesMgr classDescValuesMgr = getInflatedValues().getXMLInflateRegistry().getClassDescValuesMgr();
        ClassDescValuesChildStyle classDesc = (ClassDescValuesChildStyle)classDescValuesMgr.get("style");

        ElementValuesChild childValuesChild = classDesc.createElementValuesChildStyle(domElement, parentChildValues);
        return childValuesChild;
    }

    private ElementValues createElementValuesChildNoChildren(DOMElemValues domElement,ElementValuesResources parentChildValues)
    {
        String resourceType = ClassDescValuesChildNoChildElem.getResourceType(domElement);

        ClassDescValuesMgr classDescValuesMgr = getInflatedValues().getXMLInflateRegistry().getClassDescValuesMgr();
        ClassDescValuesChildNoChildElem classDesc = (ClassDescValuesChildNoChildElem)classDescValuesMgr.get(resourceType);
        if (classDesc == null)
            throw new ItsNatDroidException("Not found processor for resource type: " + resourceType);

        ElementValuesChild childValuesChild = classDesc.createElementValuesChildNoChildren(domElement, parentChildValues);
        return childValuesChild;
    }

}
