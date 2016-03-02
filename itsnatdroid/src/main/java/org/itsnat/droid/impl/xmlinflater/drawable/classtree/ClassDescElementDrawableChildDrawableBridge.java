package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildDrawableBridge;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
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
    public ElementDrawableChild createElementDrawableChild(DOMElemDrawable domElement, DOMElemDrawable domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable)
    {
        String name = domElement.getTagName();
        ClassDescElementDrawableRoot classDescBridgeRealTarget = (ClassDescElementDrawableRoot)getClassDescDrawableMgr().get(name);
        if (classDescBridgeRealTarget == null)
            throw new ItsNatDroidException("Not found processor for " + domElementParent.getTagName() + ":" + name);

        ElementDrawableRoot childDrawable = classDescBridgeRealTarget.createElementDrawableRoot(domElement, inflaterDrawable);
        Drawable drawable = childDrawable.getDrawable();

        return new ElementDrawableChildDrawableBridge(parentChildDrawable,classDescBridgeRealTarget,drawable);
    }

    @Override
    protected boolean isAttributeIgnored(DrawableOrElementDrawableWrapper draw,String namespaceURI,String name)
    {
        if (super.isAttributeIgnored(draw,namespaceURI,name))
            return true;

        ElementDrawableChildDrawableBridge elemDraw = (ElementDrawableChildDrawableBridge)((ElementDrawableChildContainer)draw).getElementDrawableChild();
        ClassDescElementDrawableRoot classDescBridge = elemDraw.getClassDescRootDrawableBridge();
        return classDescBridge.isAttributeIgnored(draw,namespaceURI,name);
    }

    @Override
    public boolean setAttribute(DrawableOrElementDrawableWrapper draw, DOMAttr attr, AttrDrawableContext attrCtx)
    {
        // Se redefine completamente
        ElementDrawableChildDrawableBridge elemDraw = (ElementDrawableChildDrawableBridge)((ElementDrawableChildContainer)draw).getElementDrawableChild();
        ClassDescElementDrawableRoot classDescBridge = elemDraw.getClassDescRootDrawableBridge();
        return classDescBridge.setAttribute(draw,attr,attrCtx);
    }

}
