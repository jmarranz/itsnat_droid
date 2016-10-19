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
import org.itsnat.droid.impl.xmlinflated.drawable.InflatedDrawable;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterResource;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawableChildBased;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawableChildDrawableBridge;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.DrawableOrElementDrawableWrapper;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.DrawableWrapped;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ElementDrawableChildContainer;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDescResourceBased;

import java.util.List;

/**
 * Created by jmarranz on 4/11/14.
 */
public class XMLInflaterDrawable extends XMLInflaterResource<Drawable>
{
    protected XMLInflaterDrawable(InflatedDrawable inflatedXML,int bitmapDensityReference,AttrResourceInflaterListener attrResourceInflaterListener)
    {
        super(inflatedXML,bitmapDensityReference,attrResourceInflaterListener);
    }

    public static XMLInflaterDrawable createXMLInflaterDrawable(InflatedDrawable inflatedDrawable,int bitmapDensityReference,AttrResourceInflaterListener attrResourceInflaterListener)
    {
        return new XMLInflaterDrawable(inflatedDrawable,bitmapDensityReference,attrResourceInflaterListener);
    }


    public InflatedDrawable getInflatedDrawable()
    {
        return (InflatedDrawable)inflatedXML;
    }

    public Drawable inflateDrawable()
    {
        AttrDrawableContext attrCtx = new AttrDrawableContext(this);
        return inflateElementDrawableChildRoot(getInflatedDrawable().getXMLDOMDrawable(),attrCtx).getDrawable();
    }

    private ElementDrawableChildRoot inflateElementDrawableChildRoot(XMLDOMDrawable xmlDOMDrawable, AttrDrawableContext attrCtx)
    {
        DOMElemDrawable rootDOMElem = (DOMElemDrawable)xmlDOMDrawable.getRootDOMElement();
        return createElementDrawableChildRootAndFillAttributes(rootDOMElem,attrCtx);
    }


    private ElementDrawableChildRoot createElementDrawableChildRootAndFillAttributes(DOMElemDrawable rootDOMElem, AttrDrawableContext attrCtx)
    {
        InflatedDrawable inflatedDrawable = getInflatedDrawable();

        String name = rootDOMElem.getTagName();
        ClassDescDrawableMgr classDescDrawableMgr = inflatedDrawable.getXMLInflaterRegistry().getClassDescDrawableMgr();
        ClassDescElementDrawable classDesc = (ClassDescElementDrawable)classDescDrawableMgr.get(name);
        if (classDesc == null)
            throw new ItsNatDroidException("Drawable type is not supported: " + name);
        ElementDrawableChildRoot drawableElem = createElementDrawableChildRoot(classDesc, rootDOMElem,attrCtx);
        Drawable drawable = drawableElem.getDrawable();

        inflatedDrawable.setDrawable(drawable);

        fillAttributes(classDesc, new DrawableWrapped(drawable), rootDOMElem,attrCtx);

        return drawableElem;
    }

    private ElementDrawableChildRoot createElementDrawableChildRoot(ClassDescElementDrawable classDesc, DOMElemDrawable rootDOMElem, AttrDrawableContext attrCtx)
    {
        return classDesc.createElementDrawableChildRoot(rootDOMElem,attrCtx);
    }

    private void fillAttributes(ClassDescResourceBased classDesc, DrawableOrElementDrawableWrapper drawable, DOMElemDrawable domElement, AttrDrawableContext attrCtx)
    {
        classDesc.fillResourceAttributes(drawable, domElement, attrCtx);

        /*
        Map<String,DOMAttr> attribMap = domElement.getDOMAttributes();
        if (attribMap != null)
        {
            for (Map.Entry<String,DOMAttr> entry : attribMap.entrySet())
            {
                DOMAttr attr = entry.getValue();
                setAttribute(classDesc, drawable, attr,attrCtx);
            }
        }
        */
    }

    /*
    private boolean setAttribute(ClassDescElementDrawableBased classDesc, DrawableOrElementDrawableWrapper drawable, DOMAttr attr, AttrDrawableContext attrCtx)
    {
        return classDesc.setAttribute(drawable, attr,attrCtx);
    }
    */

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
        ClassDescDrawableMgr classDescDrawableMgr = getInflatedDrawable().getXMLInflaterRegistry().getClassDescDrawableMgr();
        ClassDescElementDrawableChildBased classDesc = (ClassDescElementDrawableChildBased)classDescDrawableMgr.get(name);
        if (classDesc == null)
        {
            name = ClassDescElementDrawableChildDrawableBridge.NAME; // "*";
            classDesc = (ClassDescElementDrawableChildDrawableBridge)classDescDrawableMgr.get(name);
            if (classDesc == null) throw new ItsNatDroidException("Unexpected error"); // ClassDescElementDrawableChildDrawableBridge debe estar registrado previamente
        }

        ElementDrawableChild childDrawable = createElementDrawableChild(classDesc, domElement, domElementParent, parentChildDrawable,attrCtx);

        fillAttributes(classDesc, ElementDrawableChildContainer.create(childDrawable), domElement, attrCtx);

        return childDrawable;
    }

    private ElementDrawableChild createElementDrawableChild(ClassDescElementDrawableChildBased classDesc, DOMElemDrawable domElement, DOMElemDrawable domElementParent, ElementDrawableChildBase parentChildDrawable, AttrDrawableContext attrCtx)
    {
        return classDesc.createElementDrawableChild(domElement, domElementParent, parentChildDrawable,attrCtx);
    }

    public void processChildElements(DOMElemDrawable domElemParent, ElementDrawableChildBase parentChildDrawable, AttrDrawableContext attrCtx)
    {
        List<DOMElement> childDOMElemList = domElemParent.getChildDOMElementList();
        if (childDOMElemList == null) return;

        parentChildDrawable.initElementDrawableItemList(childDOMElemList.size());
        for (DOMElement childDOMElem : childDOMElemList)
        {
            ElementDrawableChildBase childDrawable = inflateNextElement((DOMElemDrawable)childDOMElem,domElemParent,parentChildDrawable,attrCtx);
            parentChildDrawable.addElementDrawableItem(childDrawable);
        }
    }
}
