package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildDrawableBridge;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;

/**
 * Esta clase está pensada para el drawable  que se permite como hijo único (por ejemplo el típico <bitmap>) en los casos en donde existe también el atributo android:drawable="" ej en <clip> o bajo <item>
 * en drawables basados en capas (item). Puede ser CUALQUIER DRAWABLE aunque la documentación sólo ponga el ejemplo de <bitmap>
 *
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescElementDrawableChildDrawableBridge extends ClassDescElementDrawableChild<ElementDrawableChildDrawableBridge>
{
    public static final String NAME = "*";

    public ClassDescElementDrawableChildDrawableBridge(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,NAME);
    }

    @Override
    public Class<ElementDrawableChildDrawableBridge> getDrawableOrElementDrawableClass()
    {
        return ElementDrawableChildDrawableBridge.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElement domElement, DOMElement domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        String name = domElement.getName();
        ClassDescElementDrawableRoot classDescBridgeRealTarget = (ClassDescElementDrawableRoot)getClassDescDrawableMgr().get(name);
        if (classDescBridgeRealTarget == null)
            throw new ItsNatDroidException("Not found processor for " + domElementParent.getName() + ":" + name);

        ElementDrawableRoot childDrawable = classDescBridgeRealTarget.createElementDrawableRoot(domElement, inflaterDrawable, ctx);
        Drawable drawable = childDrawable.getDrawable();

        return new ElementDrawableChildDrawableBridge(parentChildDrawable,classDescBridgeRealTarget,drawable);
    }

    @Override
    protected boolean isAttributeIgnored(DrawableOrElementDrawableContainer draw,String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(draw,namespaceURI,name))
            return true;

        ElementDrawableChildDrawableBridge elemDraw = (ElementDrawableChildDrawableBridge)((ElementDrawableChildContainer)draw).getElementDrawableChild();
        ClassDescElementDrawableRoot classDescBridge = elemDraw.getClassDescRootDrawableBridge();
        return classDescBridge.isAttributeIgnored(draw,namespaceURI,name);
    }

    @Override
    public boolean setAttribute(DrawableOrElementDrawableContainer draw,DOMAttr attr,XMLInflaterDrawable xmlInflaterDrawable, Context ctx)
    {
        // Se redefine completamente
        ElementDrawableChildDrawableBridge elemDraw = (ElementDrawableChildDrawableBridge)((ElementDrawableChildContainer)draw).getElementDrawableChild();
        ClassDescElementDrawableRoot classDescBridge = elemDraw.getClassDescRootDrawableBridge();
        return classDescBridge.setAttribute(draw,attr,xmlInflaterDrawable,ctx);
    }

}
