package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBitmapBridge;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableOrElementDrawableChildMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;

/**
 * Esta clase está pensada para el <bitmap> que se permite como hijo único en los casos en donde existe también el atributo android:drawable="" ej en <clip> o bajo <item>
 * en drawables basados en capas (item). Puede haber quizás más casos, si es así cambiar el nombre.
 *
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescElementDrawableChildBitmapBridge extends ClassDescElementDrawableChild<ElementDrawableChildBitmapBridge>
{
    public static final String NAME = "*";

    public ClassDescElementDrawableChildBitmapBridge(ClassDescDrawableOrElementDrawableChildMgr classMgr)
    {
        super(classMgr,NAME);
    }

    @Override
    public Class<ElementDrawableChildBitmapBridge> getDrawableOrElementDrawableClass()
    {
        return ElementDrawableChildBitmapBridge.class;
    }

    @Override
    public ElementDrawableChild createChildElementDrawable(DOMElement domElement, DOMElement domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        String name = domElement.getName();
        ClassDescRootElementDrawable classDescBridgeRealTarget = (ClassDescRootElementDrawable)getClassDescDrawableMgr().get(name);
        if (classDescBridgeRealTarget == null)
            throw new ItsNatDroidException("Not found processor for " + domElementParent.getName() + ":" + name);

        if (!(classDescBridgeRealTarget instanceof ClassDescBitmapDrawable))
            throw new ItsNatDroidException("Unexpected Error"); // Si hubiera más casos similares hay que quitar esto y cambiar el nombre de la clase ElementDrawableChildBitmapBridge a otra cosa

        ElementDrawableRoot childDrawable = classDescBridgeRealTarget.createRootElementDrawable(domElement, inflaterDrawable, ctx);
        Drawable drawable = childDrawable.getDrawable();

        return new ElementDrawableChildBitmapBridge(parentChildDrawable,classDescBridgeRealTarget,drawable);
    }

    @Override
    protected boolean isAttributeIgnored(DrawableOrElementDrawableContainer draw,String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(draw,namespaceURI,name))
            return true;

        ElementDrawableChildBitmapBridge elemDraw = (ElementDrawableChildBitmapBridge)((ElementDrawableChildContainer)draw).getElementDrawableChild();
        ClassDescRootElementDrawable classDescBridge = elemDraw.getClassDescRootDrawableBridge();
        return classDescBridge.isAttributeIgnored(draw,namespaceURI,name);
    }

    @Override
    public boolean setAttribute(DrawableOrElementDrawableContainer draw,DOMAttr attr,XMLInflaterDrawable xmlInflaterDrawable, Context ctx)
    {
        // Se redefine completamente
        ElementDrawableChildBitmapBridge elemDraw = (ElementDrawableChildBitmapBridge)((ElementDrawableChildContainer)draw).getElementDrawableChild();
        ClassDescRootElementDrawable classDescBridge = elemDraw.getClassDescRootDrawableBridge();
        return classDescBridge.setAttribute(draw,attr,xmlInflaterDrawable,ctx);
    }

}
