package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.LayerDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescLayerDrawable extends ClassDescElementDrawableRoot<LayerDrawable>
{
    public ClassDescLayerDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"layer-list");
    }

    @Override
    public ElementDrawableRoot createElementDrawableRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        // http://stackoverflow.com/questions/20120725/layerdrawable-programatically

        ElementDrawableRoot elementDrawableRoot = new ElementDrawableRoot();

        XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();
        xmlInflaterDrawable.processChildElements(rootElem,elementDrawableRoot,attrCtx);
        ArrayList<ElementDrawable> itemList = elementDrawableRoot.getChildElementDrawableList();

        Drawable[] drawableLayers = getDrawables(itemList);

        LayerDrawable drawable = new LayerDrawable(drawableLayers);

        for(Drawable drawableLayer : drawableLayers)
        {
            drawableLayer.setCallback(drawable);
        }

        setItemAttributes(drawable,itemList);

        elementDrawableRoot.setDrawable(drawable);

        return elementDrawableRoot;
    }

    public void setItemAttributes(LayerDrawable drawable,ArrayList<ElementDrawable> itemList)
    {
        // Es llamado este método también por ClassDescTransitionDrawable
        for (int i = 0; i < itemList.size(); i++)
        {
            LayerDrawableItem item = (LayerDrawableItem) itemList.get(i);

            drawable.setId(i, item.getId());
            drawable.setLayerInset(i, item.getLeft(), item.getTop(), item.getRight(), item.getBottom());
        }
    }

    @Override
    public Class<LayerDrawable> getDrawableOrElementDrawableClass()
    {
        return LayerDrawable.class;
    }

    protected void init()
    {
        super.init();

    }


}
