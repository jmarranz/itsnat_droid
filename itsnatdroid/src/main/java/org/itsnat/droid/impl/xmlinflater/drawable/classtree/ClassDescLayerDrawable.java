package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBase;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildRoot;
import org.itsnat.droid.impl.xmlinflated.drawable.LayerDrawableChildItem;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;

import java.util.ArrayList;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescLayerDrawable extends ClassDescElementDrawableBased<LayerDrawable> implements ClassDescCallback
{
    public ClassDescLayerDrawable(ClassDescDrawableMgr classMgr,ClassDescElementDrawableBased<? super LayerDrawable> parent)
    {
        super(classMgr,"layer-list",parent);
    }

    @Override
    public ElementDrawableChildRoot createElementDrawableChildRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        // http://stackoverflow.com/questions/20120725/layerdrawable-programatically

        ElementDrawableChildRoot elementDrawableRoot = new ElementDrawableChildRoot();

        XMLInflaterDrawable xmlInflaterDrawable = attrCtx.getXMLInflaterDrawable();
        xmlInflaterDrawable.processChildElements(rootElem,elementDrawableRoot,attrCtx);
        ArrayList<ElementDrawableChildBase> itemList = elementDrawableRoot.getElementDrawableChildList();

        Drawable[] drawableLayers = getLayerChildDrawables(itemList);

        LayerDrawable drawable = new LayerDrawable(drawableLayers);

        for(Drawable drawableLayer : drawableLayers)
        {
            setCallback(drawableLayer,drawable);
        }

        setItemAttributes(drawable,itemList);

        elementDrawableRoot.setDrawable(drawable);

        return elementDrawableRoot;
    }

    public void setItemAttributes(LayerDrawable drawable, ArrayList<ElementDrawableChildBase> itemList)
    {
        // Es llamado este método también por ClassDescTransitionDrawable
        for (int i = 0; i < itemList.size(); i++)
        {
            LayerDrawableChildItem item = (LayerDrawableChildItem) itemList.get(i);

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
