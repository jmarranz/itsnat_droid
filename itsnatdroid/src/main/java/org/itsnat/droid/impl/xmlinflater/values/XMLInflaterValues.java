package org.itsnat.droid.impl.xmlinflater.values;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.values.DOMElemValues;
import org.itsnat.droid.impl.dom.values.DOMElemValuesArrayBase;
import org.itsnat.droid.impl.dom.values.DOMElemValuesItemNormal;
import org.itsnat.droid.impl.dom.values.DOMElemValuesStyle;
import org.itsnat.droid.impl.dom.values.XMLDOMValues;
import org.itsnat.droid.impl.xmlinflated.values.ElementValues;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesArrayBase;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesItemNormal;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesResources;
import org.itsnat.droid.impl.xmlinflated.values.ElementValuesStyle;
import org.itsnat.droid.impl.xmlinflated.values.InflatedXMLValues;
import org.itsnat.droid.impl.xmlinflater.XMLInflater;
import org.itsnat.droid.impl.xmlinflater.values.classtree.ClassDescValuesArrayBase;
import org.itsnat.droid.impl.xmlinflater.values.classtree.ClassDescValuesItemNormal;
import org.itsnat.droid.impl.xmlinflater.values.classtree.ClassDescValuesStyle;

import java.util.List;

/**
 * Created by jmarranz on 4/11/14.
 */
public class XMLInflaterValues extends XMLInflater
{
    protected XMLInflaterValues(InflatedXMLValues inflatedXML, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        super(inflatedXML, bitmapDensityReference,attrResourceInflaterListener);
    }

    public static XMLInflaterValues createXMLInflaterValues(InflatedXMLValues inflatedValues, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        return new XMLInflaterValues(inflatedValues,bitmapDensityReference,attrResourceInflaterListener);
    }

    public InflatedXMLValues getInflatedXMLValues()
    {
        return (InflatedXMLValues)inflatedXML;
    }

    public ElementValuesResources inflateValues()
    {
        return inflateRoot(getInflatedXMLValues().getXMLDOMValues());
    }

    private ElementValuesResources inflateRoot(XMLDOMValues xmlDOMValues)
    {
        DOMElemValues rootDOMElem = (DOMElemValues)xmlDOMValues.getRootDOMElement();

        ElementValuesResources elementResources = new ElementValuesResources();
        getInflatedXMLValues().setRootElementResources(elementResources);

        processChildNoChildElemElements(rootDOMElem, elementResources);

        return elementResources;
    }

    private void processChildNoChildElemElements(DOMElemValues domElemParent, ElementValuesResources parentChildValues)
    {
        List<DOMElement> childDOMElemList = domElemParent.getChildDOMElementList();
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
        String tagName = domElement.getTagName();
        if ("style".equals(tagName))
        {
           childElem = createElementValuesStyle((DOMElemValuesStyle) domElement, parentChildValues);
        }
        else if ("string-array".equals(tagName) || "integer-array".equals(tagName) || "array".equals(tagName))
        {
            childElem = createElementValuesArrayBase((DOMElemValuesArrayBase) domElement, parentChildValues);
        }
        else if ("declare-styleable".equals(tagName) )
        {
            throw new ItsNatDroidException("Not supported yet: " + tagName);
        }
        else
        {
            childElem = createElementValuesItemNormal((DOMElemValuesItemNormal) domElement, parentChildValues);
        }

        // No procesamos los child elements porque cada ElementValues sabe como gestionar sus hijos si es que los tiene
        return childElem;
    }

    private ElementValuesStyle createElementValuesStyle(DOMElemValuesStyle domElement, ElementValuesResources parentChildValues)
    {
        ClassDescValuesMgr classDescValuesMgr = getInflatedXMLValues().getXMLInflaterRegistry().getClassDescValuesMgr();
        ClassDescValuesStyle classDesc = (ClassDescValuesStyle)classDescValuesMgr.get("style");

        ElementValuesStyle childValuesChild = classDesc.createElementValuesStyle(domElement, parentChildValues);
        return childValuesChild;
    }

    private ElementValuesArrayBase createElementValuesArrayBase(DOMElemValuesArrayBase domElement, ElementValuesResources parentChildValues)
    {
        ClassDescValuesMgr classDescValuesMgr = getInflatedXMLValues().getXMLInflaterRegistry().getClassDescValuesMgr();
        ClassDescValuesArrayBase classDesc = (ClassDescValuesArrayBase)classDescValuesMgr.get(domElement.getTagName());

        ElementValuesArrayBase childValuesChild = classDesc.createElementValuesArrayBase(domElement, parentChildValues);
        return childValuesChild;
    }

    private ElementValuesItemNormal createElementValuesItemNormal(DOMElemValuesItemNormal domElement, ElementValuesResources parentChildValues)
    {
        String resourceType = ClassDescValuesItemNormal.getResourceTypeItemNormal(domElement);

        ClassDescValuesMgr classDescValuesMgr = getInflatedXMLValues().getXMLInflaterRegistry().getClassDescValuesMgr();
        ClassDescValuesItemNormal classDesc = (ClassDescValuesItemNormal)classDescValuesMgr.get(resourceType);
        if (classDesc == null)
            throw new ItsNatDroidException("Not found processor for resource type: " + resourceType);

        ElementValuesItemNormal childValuesChild = classDesc.createElementValuesItemNormal(domElement, parentChildValues);
        return childValuesChild;
    }

}
