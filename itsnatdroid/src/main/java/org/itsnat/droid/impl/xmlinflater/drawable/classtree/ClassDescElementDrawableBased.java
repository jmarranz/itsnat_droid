package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.util.NamespaceUtil;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBase;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildDrawableBridge;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildWithDrawable;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterContext;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.shared.classtree.ClassDescResourceBased;

import java.util.ArrayList;

/**
 * Created by Jose on 15/10/2015.
 */
public abstract class ClassDescElementDrawableBased<Tdrawable extends Drawable> extends ClassDescResourceBased<Tdrawable,AttrDrawableContext>
{
    public ClassDescElementDrawableBased(ClassDescDrawableMgr classMgr, String tagName, ClassDescElementDrawableBased<? super Tdrawable> parentClass)
    {
        super(classMgr, tagName, parentClass);
    }

    public abstract ElementDrawableChildRoot createElementDrawableChildRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx);


    @Override
    protected Tdrawable createResourceNative(Context ctx)
    {
        return null; // No se usa
    }

    public Class<Tdrawable> getDeclaredClass()
    {
        return getDrawableOrElementDrawableClass();
    }

    public abstract Class<Tdrawable> getDrawableOrElementDrawableClass();

    public ClassDescDrawableMgr getClassDescDrawableMgr()
    {
        return (ClassDescDrawableMgr) classMgr;
    }

    public ClassDescElementDrawableBased getParentClassDescDrawable()
    {
        return (ClassDescElementDrawableBased) getParentClassDesc();
    }

    public String getElementName()
    {
        return getClassOrDOMElemName();
    }

    public Drawable getDrawableChild(String drawableAttrName, DOMElemDrawable domElement, XMLInflaterContext xmlInflaterContext, ArrayList<ElementDrawableChildBase> childList)
    {
        XMLInflaterRegistry xmlInflaterRegistry = classMgr.getXMLInflaterRegistry();

        // Si el drawable está definido como elemento hijo gana éste por delante del atributo drawable
        DOMAttr attrDrawable = domElement.getDOMAttribute(NamespaceUtil.XMLNS_ANDROID, drawableAttrName); // Puede ser nulo, en dicho caso el drawable debe estar definido inline como elemento hijo
        Drawable drawable = attrDrawable != null ? xmlInflaterRegistry.getDrawable(attrDrawable.getResourceDesc(), xmlInflaterContext) : null;

        if (childList != null)
        {
            if (childList.size() == 1) {
                // Si existe un drawable hijo gana el hijo sobre el drawable definido como atributo
                ElementDrawableChildDrawableBridge childDrawable = (ElementDrawableChildDrawableBridge) childList.get(0);
                drawable = childDrawable.getDrawable();
            }
            else if (childList.size() > 1)
                throw new ItsNatDroidException("Expected just a single child element or none, processing " + getElementName());
        }

        if (drawable == null)
            throw new ItsNatDroidException("Drawable is not defined in drawable attribute or as a child element, processing " + getElementName());

        return drawable; // Puede ser null
    }


    public static Drawable[] getLayerChildDrawables(ArrayList<ElementDrawableChildBase> itemList)
    {
        Drawable[] drawableLayers = new Drawable[itemList.size()];
        for (int i = 0; i < itemList.size(); i++)
        {
            ElementDrawableChildWithDrawable item = (ElementDrawableChildWithDrawable) itemList.get(i);
            drawableLayers[i] = item.getDrawable();
        }
        return drawableLayers;
    }

    public void setCallback(Drawable childDrawable, Drawable.Callback parentDrawable)
    {
        childDrawable.setCallback(parentDrawable);
    }
}
