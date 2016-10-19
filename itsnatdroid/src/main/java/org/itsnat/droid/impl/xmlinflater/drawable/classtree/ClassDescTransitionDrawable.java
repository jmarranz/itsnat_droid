package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBase;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.TransitionDrawableChildItem;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescTransitionDrawable extends ClassDescElementDrawableBased<TransitionDrawable> implements ClassDescCallback<LayerDrawable> // TransitionDrawable deriva de LayerDrawable
{
    public ClassDescTransitionDrawable(ClassDescDrawableMgr classMgr,ClassDescElementDrawableBased<? super TransitionDrawable> parent)
    {
        super(classMgr,"transition",parent);
    }

    public ClassDescLayerDrawable getParentClassDescDrawable()
    {
        return (ClassDescLayerDrawable)getParentClassDesc();
    }

    @Override
    public ElementDrawableChildRoot createElementDrawableChildRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        ElementDrawableChildRoot elementDrawableRoot = new ElementDrawableChildRoot();

        XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();

        xmlInflaterDrawable.processChildElements(rootElem,elementDrawableRoot,attrCtx);
        ArrayList<ElementDrawableChildBase> itemList = elementDrawableRoot.getElementDrawableChildList();

        {
            ((TransitionDrawableChildItem) itemList.get(0)).getDrawable(); // Just a check
        }

        Drawable[] drawableLayers = getLayerChildDrawables(itemList);

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


    }


}
