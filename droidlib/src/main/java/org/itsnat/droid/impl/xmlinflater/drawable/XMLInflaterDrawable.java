package org.itsnat.droid.impl.xmlinflater.drawable;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.itsnat.droid.AttrDrawableInflaterListener;
import org.itsnat.droid.AttrLayoutInflaterListener;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.dom.drawable.XMLDOMDrawable;
import org.itsnat.droid.impl.dom.layout.DOMElemLayout;
import org.itsnat.droid.impl.dom.layout.DOMElemMerge;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.InflatedDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.InflatedDrawablePage;
import org.itsnat.droid.impl.xmlinflated.drawable.InflatedDrawableStandalone;
import org.itsnat.droid.impl.xmlinflater.XMLInflater;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawableChild;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawableChildDrawableBridge;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.DrawableOrElementDrawableWrapper;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.DrawableWrapped;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ElementDrawableChildContainer;
import org.itsnat.droid.impl.xmlinflater.drawable.page.XMLInflaterDrawablePage;
import org.itsnat.droid.impl.xmlinflater.drawable.stdalone.XMLInflaterDrawableStandalone;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by jmarranz on 4/11/14.
 */
public abstract class XMLInflaterDrawable extends XMLInflater
{
    protected XMLInflaterDrawable(InflatedDrawable inflatedXML,int bitmapDensityReference,AttrLayoutInflaterListener attrLayoutInflaterListener,AttrDrawableInflaterListener attrDrawableInflaterListener)
    {
        super(inflatedXML,bitmapDensityReference,attrLayoutInflaterListener,attrDrawableInflaterListener);
    }

    public static XMLInflaterDrawable createXMLInflaterDrawable(InflatedDrawable inflatedDrawable,int bitmapDensityReference,AttrLayoutInflaterListener attrLayoutInflaterListener,AttrDrawableInflaterListener attrDrawableInflaterListener)
    {
        if (inflatedDrawable instanceof InflatedDrawablePage)
        {
            return new XMLInflaterDrawablePage((InflatedDrawablePage)inflatedDrawable,bitmapDensityReference,attrLayoutInflaterListener,attrDrawableInflaterListener);
        }
        else if (inflatedDrawable instanceof InflatedDrawableStandalone)
        {
            return new XMLInflaterDrawableStandalone((InflatedDrawableStandalone)inflatedDrawable,bitmapDensityReference,attrLayoutInflaterListener,attrDrawableInflaterListener);
        }
        return null; // Internal Error
    }


    public InflatedDrawable getInflatedDrawable()
    {
        return (InflatedDrawable)inflatedXML;
    }

    public Drawable inflateDrawable()
    {
        return inflateRoot(getInflatedDrawable().getXMLDOMDrawable()).getDrawable();
    }

    private ElementDrawableRoot inflateRoot(XMLDOMDrawable xmlDOMDrawable)
    {
        DOMElemDrawable rootDOMElem = (DOMElemDrawable)xmlDOMDrawable.getRootDOMElement();
        return createRootDrawableAndFillAttributes(rootDOMElem,getInflatedDrawable());
    }


    private ElementDrawableRoot createRootDrawableAndFillAttributes(DOMElemDrawable rootDOMElem,InflatedDrawable inflatedDrawable)
    {
        String name = rootDOMElem.getName();
        ClassDescDrawableMgr classDescViewMgr = getInflatedDrawable().getXMLInflateRegistry().getClassDescDrawableMgr();
        ClassDescElementDrawableRoot<? extends Drawable> classDesc = (ClassDescElementDrawableRoot<? extends Drawable>)classDescViewMgr.get(name);
        if (classDesc == null) // Aquí no hay una clase View que sea raiz de todos
            throw new ItsNatDroidException("Drawable type is not supported: " + name);
        ElementDrawableRoot drawableElem = createRootElementDrawable(classDesc, rootDOMElem);
        Drawable drawable = drawableElem.getDrawable();

        inflatedDrawable.setDrawable(drawable);

        fillAttributes(classDesc, new DrawableWrapped(drawable), rootDOMElem);

        return drawableElem;
    }

    private ElementDrawableRoot createRootElementDrawable(ClassDescElementDrawableRoot classDesc, DOMElemDrawable rootDOMElem)
    {
        Context ctx = getContext();
        return classDesc.createElementDrawableRoot(rootDOMElem, this, ctx);
    }

    private void fillAttributes(ClassDescDrawable classDesc,DrawableOrElementDrawableWrapper drawable,DOMElemDrawable domElement)
    {
        ArrayList<DOMAttr> attribList = domElement.getDOMAttributeList();
        if (attribList != null)
        {
            AttrDrawableContext attrCtx = new AttrDrawableContext(this);
            for (int i = 0; i < attribList.size(); i++)
            {
                DOMAttr attr = attribList.get(i);
                setAttribute(classDesc, drawable, attr,attrCtx);
            }
        }
    }

    private boolean setAttribute(ClassDescDrawable classDesc,DrawableOrElementDrawableWrapper drawable,DOMAttr attr,AttrDrawableContext attrCtx)
    {
        return classDesc.setAttribute(drawable, attr,attrCtx);
    }


    protected ElementDrawable inflateNextElement(DOMElemDrawable domElement,DOMElemDrawable domElementParent,ElementDrawable parentChildDrawable)
    {
        ElementDrawableChild childDrawable = createElementDrawableChildAndFillAttributes(domElement, domElementParent, parentChildDrawable);

        processChildElements(domElement,childDrawable);

        return childDrawable;
    }

    private void getFullName(DOMElemDrawable domElement,StringBuilder name)
    {
        if (domElement.getParentDOMElement() != null)
        {
            getFullName((DOMElemDrawable)domElement.getParentDOMElement(),name);
            name.append(':');
        }
        name.append(domElement.getName());
    }

    private String getFullName(DOMElemDrawable domElement)
    {
        StringBuilder name = new StringBuilder();
        getFullName(domElement,name);
        return name.toString();
    }

    private ElementDrawableChild createElementDrawableChildAndFillAttributes(DOMElemDrawable domElement, DOMElemDrawable domElementParent, ElementDrawable parentChildDrawable)
    {
        String parentName = getFullName(domElementParent);
        String name = parentName + ":" + domElement.getName();
        ClassDescDrawableMgr classDescViewMgr = getInflatedDrawable().getXMLInflateRegistry().getClassDescDrawableMgr();
        ClassDescElementDrawableChild classDesc = (ClassDescElementDrawableChild)classDescViewMgr.get(name);
        if (classDesc == null)
        {
            name = ClassDescElementDrawableChildDrawableBridge.NAME; // "*";
            classDesc = (ClassDescElementDrawableChildDrawableBridge)classDescViewMgr.get(name);
            if (classDesc == null) throw new ItsNatDroidException("Unexpected error"); // ClassDescElementDrawableChildDrawableBridge debe estar registrado previamente
        }

        ElementDrawableChild childDrawable = createElementDrawableChild(classDesc, domElement, domElementParent, parentChildDrawable);

        fillAttributes(classDesc, ElementDrawableChildContainer.create(childDrawable), domElement);

        return childDrawable;
    }

    private ElementDrawableChild createElementDrawableChild(ClassDescElementDrawableChild classDesc, DOMElemDrawable domElement, DOMElemDrawable domElementParent, ElementDrawable parentChildDrawable)
    {
        return classDesc.createElementDrawableChild(domElement, domElementParent, this, parentChildDrawable,getContext());
    }

    public void processChildElements(DOMElemDrawable domElemParent,ElementDrawable parentChildDrawable)
    {
        LinkedList<DOMElement> childDOMElemList = domElemParent.getChildDOMElementList();
        if (childDOMElemList == null) return;

        parentChildDrawable.initChildElementDrawableList(childDOMElemList.size());
        for (DOMElement childDOMElem : childDOMElemList)
        {
            ElementDrawable childDrawable = inflateNextElement((DOMElemDrawable)childDOMElem,domElemParent,parentChildDrawable);
            parentChildDrawable.addChildElementDrawable(childDrawable);
        }
    }
}
