package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBase;
import org.itsnat.droid.impl.xmlinflated.drawable.LayerDrawableChildItem;
import org.itsnat.droid.impl.xmlinflated.drawable.TransitionDrawableChildItem;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescTransitionDrawableChildItem extends ClassDescElementDrawableChildWithDrawable<LayerDrawableChildItem> // Es correcto poner la clase base (Layer)
{
    public ClassDescTransitionDrawableChildItem(ClassDescDrawableMgr classMgr, ClassDescLayerDrawableChildItem parentClass) // Es correcto ClassDescLayerDrawableItem es el ClassDesc base
    {
        super(classMgr,"transition:item",parentClass);
    }

    @Override
    public Class<TransitionDrawableChildItem> getDrawableOrElementDrawableClass()
    {
        return TransitionDrawableChildItem.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElemDrawable domElement, DOMElemDrawable domElementParent, ElementDrawableChildBase parentChildDrawable, AttrDrawableContext attrCtx)
    {
        return new TransitionDrawableChildItem(parentChildDrawable);
    }

    protected void init()
    {
        super.init();

        // Se definen en la clase base item de LayerDrawable
    }

}
