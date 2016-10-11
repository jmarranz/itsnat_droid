package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.TransitionDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawable_Drawable_visible;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescTransitionDrawable extends ClassDescElementDrawableRoot<TransitionDrawable> implements ClassDescCallback<LayerDrawable>
{
    public ClassDescTransitionDrawable(ClassDescDrawableMgr classMgr,ClassDescLayerDrawable parentClass)
    {
        super(classMgr,"transition",parentClass);
    }

    public ClassDescLayerDrawable getParentClassDescDrawable()
    {
        return (ClassDescLayerDrawable)getParentClassDesc();
    }

    @Override
    public ElementDrawableRoot createElementDrawableRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();

        xmlInflaterDrawable.processChildElements(rootElem,elementDrawableRoot,attrCtx);
        ArrayList<ElementDrawable> itemList = elementDrawableRoot.getChildElementDrawableList();

        {
            ((TransitionDrawableItem) itemList.get(0)).getDrawable(); // Just a check
        }

        Drawable[] drawableLayers = getDrawables(itemList);

        TransitionDrawable drawable = new TransitionDrawable(drawableLayers);

        for(Drawable drawableLayer : drawableLayers)
        {
            setCallback(drawableLayer,drawable);
        }

        ClassDescLayerDrawable parentClassDesc = getParentClassDescDrawable();

        parentClassDesc.setItemAttributes(drawable, itemList);

        elementDrawableRoot.setDrawable(drawable);

        return elementDrawableRoot;
    }

    @Override
    public void setCallback(Drawable childDrawable, LayerDrawable parentDrawable)
    {
        childDrawable.setCallback(parentDrawable);
    }

    @Override
    public Class<TransitionDrawable> getDrawableOrElementDrawableClass()
    {
        return TransitionDrawable.class;
    }

    protected void init()
    {
        super.init();

        // Se implementa en Drawable pero con el lio de clases base lo declaramos aquí:
        addAttrDescAN(new AttrDescDrawable_Drawable_visible<Drawable>(this));
    }


}
