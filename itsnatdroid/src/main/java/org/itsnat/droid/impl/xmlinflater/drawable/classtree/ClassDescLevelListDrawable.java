package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBase;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.LevelListDrawableChildItem;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescLevelListDrawable extends ClassDescElementDrawableBased<LevelListDrawable>
{
    public ClassDescLevelListDrawable(ClassDescDrawableMgr classMgr,ClassDescDrawableContainer parent)
    {
        super(classMgr, "level-list", parent);
    }

    @Override
    public ElementDrawableChildRoot createElementDrawableChildRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        ElementDrawableChildRoot elementDrawableRoot = new ElementDrawableChildRoot();

        XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();
        xmlInflaterDrawable.processChildElements(rootElem,elementDrawableRoot,attrCtx);
        ArrayList<ElementDrawableChildBase> itemList = elementDrawableRoot.getElementDrawableChildList();

        LevelListDrawable drawable = new LevelListDrawable();

        for(int i = 0; i < itemList.size(); i++)
        {
            LevelListDrawableChildItem item = (LevelListDrawableChildItem)itemList.get(i);

            Drawable drawableChild = item.getDrawable();

            Integer minObj = item.getMinLevel();
            Integer maxObj = item.getMaxLevel();
            int min = minObj != null ? minObj : 0; // Según el código fuente
            int max = maxObj != null ? maxObj : 0; // Según el código fuente

            drawable.addLevel(min,max,drawableChild);

            setCallback(drawableChild,drawable); // Se puede ver en el código fuente si se sigue hasta addChild(Drawable dr)
        }

        elementDrawableRoot.setDrawable(drawable);

        return elementDrawableRoot;
    }

    @Override
    public void setCallback(Drawable childDrawable, LevelListDrawable parentDrawable)
    {
        childDrawable.setCallback(parentDrawable);
    }

    @Override
    public Class<LevelListDrawable> getDrawableOrElementDrawableClass()
    {
        return LevelListDrawable.class;
    }

    protected void init()
    {
        super.init();



        // yo creo que sobra este atributo
        // addAttrDescAN(new AttrDescReflecMethodBoolean(this, "oneshot", "setOneShot", false));
    }


}
