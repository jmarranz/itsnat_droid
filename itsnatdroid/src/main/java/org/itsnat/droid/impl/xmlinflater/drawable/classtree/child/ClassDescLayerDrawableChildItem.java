package org.itsnat.droid.impl.xmlinflater.drawable.classtree.child;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBase;
import org.itsnat.droid.impl.xmlinflated.drawable.LayerDrawableChildItem;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntFloor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodId;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescLayerDrawableChildItem extends ClassDescElementDrawableChildWithDrawable<LayerDrawableChildItem>
{
    public ClassDescLayerDrawableChildItem(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"layer-list:item",null);
    }

    @Override
    public Class<LayerDrawableChildItem> getDrawableOrElementDrawableClass()
    {
        return LayerDrawableChildItem.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElemDrawable domElement, DOMElemDrawable domElementParent, ElementDrawableChildBase parentChildDrawable, AttrDrawableContext attrCtx)
    {
        return new LayerDrawableChildItem(parentChildDrawable);
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
