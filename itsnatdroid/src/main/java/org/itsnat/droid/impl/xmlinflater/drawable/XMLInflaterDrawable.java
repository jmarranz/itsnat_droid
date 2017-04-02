package org.itsnat.droid.impl.xmlinflater.drawable;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.dom.drawable.XMLDOMDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBase;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.InflatedXMLDrawable;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterResource;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawableBased;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.child.ClassDescElementDrawableChildBased;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.child.ClassDescElementDrawableChildDrawableBridge;

import java.util.List;

/**
 * Created by jmarranz on 4/11/14.
 */
public class XMLInflaterDrawable extends XMLInflaterResource<Drawable>
{
    protected XMLInflaterDrawable(InflatedXMLDrawable inflatedXML, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        super(inflatedXML,bitmapDensityReference,attrResourceInflaterListener);
    }

    public static XMLInflaterDrawable createXMLInflaterDrawable(InflatedXMLDrawable inflatedDrawable, int bitmapDensityReference, AttrResourceInflaterListener attrResourceInflaterListener)
    {
        return new XMLInflaterDrawable(inflatedDrawable,bitmapDensityReference,attrResourceInflaterListener);
    }


    public InflatedXMLDrawable getInflatedXMLDrawable()
    {
        return (InflatedXMLDrawable)inflatedXML;
    }

    public Drawable inflateDrawable()
    {
        AttrDrawableContext attrCtx = new AttrDrawableContext(this);
        return inflateElementDrawableChildRoot(getInflatedXMLDrawable().getXMLDOMDrawable(),attrCtx).getDrawable();
    }

    private ElementDrawableChildRoot inflateElementDrawableChildRoot(XMLDOMDrawable xmlDOMDrawable, AttrDrawableContext attrCtx)
    {
        DOMElemDrawable rootDOMElem = (DOMElemDrawable)xmlDOMDrawable.getRootDOMElement();
        return createElementDrawableChildRootAndFillAttributes(rootDOMElem,attrCtx);
    }


    private ElementDrawableChildRoot createElementDrawableChildRootAndFillAttributes(DOMElemDrawable rootDOMElem, AttrDrawableContext attrCtx)
    {
        InflatedXMLDrawable inflatedDrawable = getInflatedXMLDrawable();

        String name = rootDOMElem.getTagName();
        ClassDescDrawableMgr classDescDrawableMgr = inflatedDrawable.getXMLInflaterRegistry().getClassDescDrawableMgr();

        ClassDescElementDrawableBased classDesc = (ClassDescElementDrawableBased)classDescDrawableMgr.get(name);
        if (classDesc == null)
            throw new ItsNatDroidException("Drawable type is not supported: " + name);
        ElementDrawableChildRoot drawableElem = createElementDrawableChildRoot(classDesc, rootDOMElem,attrCtx);
        Drawable drawable = drawableElem.getDrawable();

        inflatedDrawable.setDrawable(drawable);

        fillAttributes(classDesc, drawable, rootDOMElem,attrCtx);

        return drawableElem;
    }

    private ElementDrawableChildRoot createElementDrawableChildRoot(ClassDescElementDrawableBased classDesc, DOMElemDrawable rootDOMElem, AttrDrawableContext attrCtx)
    {
        return classDesc.createElementDrawableChildRoot(rootDOMElem,attrCtx);
    }

    @SuppressWarnings("unchecked")
    private void fillAttributes(ClassDescElementDrawableBased classDesc, Drawable drawable, DOMElemDrawable domElement, AttrDrawableContext attrCtx)
    {
        classDesc.fillResourceAttributes(drawable, domElement, attrCtx);
    }


    protected ElementDrawableChildBase inflateNextElement(DOMElemDrawable domElement, DOMElemDrawable domElementParent, ElementDrawableChildBase parentChildDrawable, AttrDrawableContext attrCtx)
    {
        ElementDrawableChild childDrawable = createElementDrawableChildAndFillAttributes(domElement, domElementParent, parentChildDrawable,attrCtx);

        processChildElements(domElement,childDrawable,attrCtx);

        return childDrawable;
    }

    private void getFullName(DOMElemDrawable domElement,StringBuilder name)
    {
        if (domElement.getParentDOMElement() != null)
        {
            getFullName((DOMElemDrawable)domElement.getParentDOMElement(),name);
            name.append(':');
        }
        name.append(domElement.getTagName());
    }

    private String getFullName(DOMElemDrawable domElement)
    {
        StringBuilder name = new StringBuilder();
        getFullName(domElement,name);
        return name.toString();
    }

    private ElementDrawableChild createElementDrawableChildAndFillAttributes(DOMElemDrawable domElement, DOMElemDrawable domElementParent, ElementDrawableChildBase parentChildDrawable, AttrDrawableContext attrCtx)
    {
        String parentName = getFullName(domElementParent);
        String name = parentName + ":" + domElement.getTagName();
        ClassDescDrawableMgr classDescDrawableMgr = getInflatedXMLDrawable().getXMLInflaterRegistry().getClassDescDrawableMgr();
        ClassDescElementDrawableChildBased classDesc = (ClassDescElementDrawableChildBased)classDescDrawableMgr.get(name);
        if (classDesc == null)
        {
            name = ClassDescElementDrawableChildDrawableBridge.NAME; // "*";
            classDesc = (ClassDescElementDrawableChildDrawableBridge)classDescDrawableMgr.get(name);
            if (classDesc == null) throw new ItsNatDroidException("Unexpected error"); // ClassDescElementDrawableChildDrawableBridge debe estar registrado previamente
        }

        ElementDrawableChild drawableChild = createElementDrawableChild(classDesc, domElement, domElementParent, parentChildDrawable,attrCtx);

        fillAttributes(classDesc,drawableChild , domElement, attrCtx); // ElementDrawableChildContainer.create(drawableChild)

        return drawableChild;
    }


    private ElementDrawableChild createElementDrawableChild(ClassDescElementDrawableChildBased classDesc, DOMElemDrawable domElement, DOMElemDrawable domElementParent, ElementDrawableChildBase parentChildDrawable, AttrDrawableContext attrCtx)
    {
        return classDesc.createElementDrawableChild(domElement, domElementParent, parentChildDrawable,attrCtx);
    }

    @SuppressWarnings("unchecked")
    private void fillAttributes(ClassDescElementDrawableChildBased classDesc, ElementDrawableChild drawableChild, DOMElemDrawable domElement, AttrDrawableContext attrCtx)
    {
        classDesc.fillResourceAttributes(drawableChild, domElement, attrCtx);
    }

    public void processChildElements(DOMElemDrawable domElemParent, ElementDrawableChildBase parentChildDrawable, AttrDrawableContext attrCtx)
    {
        List<DOMElement> childDOMElemList = domElemParent.getChildDOMElementList();
        if (childDOMElemList == null) return;

        parentChildDrawable.initElementDrawableChildList(childDOMElemList.size());
        for (DOMElement childDOMElem : childDOMElemList)
        {
            ElementDrawableChildBase childDrawable = inflateNextElement((DOMElemDrawable)childDOMElem,domElemParent,parentChildDrawable,attrCtx);
            parentChildDrawable.addElementDrawableChild(childDrawable);
        }
    }
}
