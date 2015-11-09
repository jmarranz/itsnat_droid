package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.LevelListDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescLevelListDrawable extends ClassDescDrawableContainer<LevelListDrawable>
{
    public ClassDescLevelListDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"level-list");
    }

    @Override
    public ElementDrawableRoot createElementDrawableRoot(DOMElement rootElem, XMLInflaterDrawable inflaterDrawable, Context ctx)
    {
        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        inflaterDrawable.processChildElements(rootElem,elementDrawableRoot);
        ArrayList<ElementDrawable> itemList = elementDrawableRoot.getChildElementDrawableList();

        LevelListDrawable drawable = new LevelListDrawable();

        for(int i = 0; i < itemList.size(); i++)
        {
            LevelListDrawableItem item = (LevelListDrawableItem)itemList.get(i);

            Drawable drawableItem = item.getDrawable();

            Integer minObj = item.getMinLevel();
            Integer maxObj = item.getMaxLevel();
            int min = minObj != null ? minObj : 0; // Según el código fuente
            int max = maxObj != null ? maxObj : 0; // Según el código fuente

            drawable.addLevel(min,max,drawableItem);
        }

        elementDrawableRoot.setDrawable(drawable);

        return elementDrawableRoot;
    }


    @Override
    public Class<LevelListDrawable> getDrawableOrElementDrawableClass()
    {
        return LevelListDrawable.class;
    }

    protected void init()
    {
        super.init();

    }


}
