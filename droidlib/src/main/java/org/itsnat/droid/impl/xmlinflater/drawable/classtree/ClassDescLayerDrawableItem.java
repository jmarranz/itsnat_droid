package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.LayerDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodDimensionIntFloor;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodId;

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
    public ElementDrawableChild createElementDrawableChild(DOMElement domElement, DOMElement domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        return new LayerDrawableItem(parentChildDrawable);
    }

    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescDrawableReflecMethodDimensionIntFloor(this,"bottom"));
        addAttrDesc(new AttrDescDrawableReflecMethodDrawable(this,"drawable"));
        addAttrDesc(new AttrDescDrawableReflecMethodId(this,"id"));
        addAttrDesc(new AttrDescDrawableReflecMethodDimensionIntFloor(this,"left"));
        // android:paddingMode es level 21
        addAttrDesc(new AttrDescDrawableReflecMethodDimensionIntFloor(this,"right"));
        addAttrDesc(new AttrDescDrawableReflecMethodDimensionIntFloor(this,"top"));
    }

}
