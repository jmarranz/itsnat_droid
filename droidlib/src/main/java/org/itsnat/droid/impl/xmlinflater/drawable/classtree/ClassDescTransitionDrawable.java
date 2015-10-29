package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.TransitionDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescTransitionDrawable extends ClassDescElementDrawableRoot<TransitionDrawable>
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
    public ElementDrawableRoot createRootElementDrawable(DOMElement rootElem, XMLInflaterDrawable inflaterDrawable, Context ctx)
    {
        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        inflaterDrawable.processChildElements(rootElem,elementDrawableRoot);
        ArrayList<ElementDrawable> itemList = elementDrawableRoot.getChildElementDrawableList();

        {
            ((TransitionDrawableItem) itemList.get(0)).getDrawable(); // Just a check
        }

        Drawable[] drawableLayers = getDrawables(itemList);

        TransitionDrawable drawable = new TransitionDrawable(drawableLayers);

        ClassDescLayerDrawable parentClassDesc = getParentClassDescDrawable();

        parentClassDesc.setItemAttributes(drawable, itemList);

        return new ElementDrawableRoot(drawable,itemList);
    }


    @Override
    public Class<TransitionDrawable> getDrawableOrElementDrawableClass()
    {
        return TransitionDrawable.class;
    }

    protected void init()
    {
        super.init();

        //addAttrDesc(new AttrDescDrawableReflecMethodBoolean(this, "dither", true));
    }


}
