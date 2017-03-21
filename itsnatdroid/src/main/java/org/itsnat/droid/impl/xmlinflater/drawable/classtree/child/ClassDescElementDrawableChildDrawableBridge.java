package org.itsnat.droid.impl.xmlinflater.drawable.classtree.child;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBase;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildDrawableBridge;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildRoot;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescElementDrawableBased;

/**
 * Esta clase está pensada para el drawable  que se permite como hijo único (por ejemplo el típico <bitmap>) en los casos en donde existe también el atributo android:drawable="" ej en <clip> o bajo <item>
 * en drawables basados en capas (item). Puede ser CUALQUIER DRAWABLE aunque la documentación sólo ponga el ejemplo de <bitmap>
 *
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescElementDrawableChildDrawableBridge extends ClassDescElementDrawableChildBased<ElementDrawableChildDrawableBridge>
{
    public static final String NAME = "*";

    public ClassDescElementDrawableChildDrawableBridge(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,NAME,null);
    }

    @Override
    public Class<ElementDrawableChildDrawableBridge> getDrawableOrElementDrawableClass()
    {
        return ElementDrawableChildDrawableBridge.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElemDrawable domElement, DOMElemDrawable domElementParent, ElementDrawableChildBase parentChildDrawable, AttrDrawableContext attrCtx)
    {
        String name = domElement.getTagName();
        @SuppressWarnings("unchecked") ClassDescElementDrawableBased<Drawable> classDescBridgeRealTarget = (ClassDescElementDrawableBased<Drawable>)getClassDescDrawableMgr().get(name);
        if (classDescBridgeRealTarget == null)
            throw new ItsNatDroidException("Not found processor for " + domElementParent.getTagName() + ":" + name);

        ElementDrawableChildRoot childDrawable = classDescBridgeRealTarget.createElementDrawableChildRoot(domElement,attrCtx);
        Drawable drawable = childDrawable.getDrawable();

        return new ElementDrawableChildDrawableBridge(parentChildDrawable,classDescBridgeRealTarget,drawable);
    }


    @Override
    @SuppressWarnings("unchecked")
    public boolean isAttributeIgnored(ElementDrawableChildDrawableBridge resource, String namespaceURI, String name)
    {
        if (super.isAttributeIgnored(resource,namespaceURI,name))
            return true;

        ClassDescElementDrawableBased<Drawable> classDescBridge = resource.getClassDescElementDrawableBasedBridgeRoot();
        return classDescBridge.isAttributeIgnored(resource.getDrawable(),namespaceURI,name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setAttribute(ElementDrawableChildDrawableBridge resource, DOMAttr attr, AttrDrawableContext attrCtx)
    {
        // Se redefine completamente
        ClassDescElementDrawableBased<Drawable> classDescBridge = resource.getClassDescElementDrawableBasedBridgeRoot();
        return classDescBridge.setAttribute(resource.getDrawable(),attr,attrCtx);
    }

}
