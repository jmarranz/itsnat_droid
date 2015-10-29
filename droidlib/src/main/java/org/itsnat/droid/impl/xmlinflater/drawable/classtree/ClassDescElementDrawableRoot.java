package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableContainer;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.TransitionDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;

import java.util.ArrayList;

/**
 * Created by jmarranz on 27/11/14.
 */
public abstract class ClassDescElementDrawableRoot<Tdrawable extends Drawable> extends ClassDescDrawable
{
    public ClassDescElementDrawableRoot(ClassDescDrawableMgr classMgr, String className)
    {
        super(classMgr, className, null);
    }

    public ClassDescElementDrawableRoot(ClassDescDrawableMgr classMgr, String className,ClassDescDrawable parentClass)
    {
        super(classMgr, className, parentClass);
    }

    public static Drawable[] getDrawables(ArrayList<ElementDrawable> itemList)
    {
        Drawable[] drawableLayers = new Drawable[itemList.size()];
        for (int i = 0; i < itemList.size(); i++)
        {
            ElementDrawableContainer item = (ElementDrawableContainer) itemList.get(i);
            drawableLayers[i] = item.getDrawable();
        }
        return drawableLayers;
    }

    public abstract ElementDrawableRoot createRootElementDrawable(DOMElement rootElem, XMLInflaterDrawable inflaterDrawable, Context ctx);
}

