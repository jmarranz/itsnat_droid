package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.LayerDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntFloor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodId;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescLayerDrawableItem extends ClassDescElementDrawableChildWithDrawable<LayerDrawableItem>
{
    public ClassDescLayerDrawableItem(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"layer-list:item");
    }

    @Override
    public Class<LayerDrawableItem> getDrawableOrElementDrawableClass()
    {
        return LayerDrawableItem.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElemDrawable domElement, DOMElemDrawable domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        return new LayerDrawableItem(parentChildDrawable);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodDimensionIntFloor(this, "bottom", 0f));
        addAttrDescAN(new AttrDescReflecMethodDrawable(this, "drawable", "@null"));
        addAttrDescAN(new AttrDescReflecMethodId(this, "id", -1));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntFloor(this, "left", 0f));
        // android:paddingMode es level 21
        addAttrDescAN(new AttrDescReflecMethodDimensionIntFloor(this, "right", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntFloor(this, "top", 0f));
    }

}
