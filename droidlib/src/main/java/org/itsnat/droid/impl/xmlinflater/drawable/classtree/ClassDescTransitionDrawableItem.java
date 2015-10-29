package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.LayerDrawableItem;
import org.itsnat.droid.impl.xmlinflated.drawable.TransitionDrawableItem;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodDimensionFloatFloor;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodDimensionIntFloor;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodDrawable;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawableReflecMethodId;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescTransitionDrawableItem extends ClassDescElementDrawableChildNormal<TransitionDrawableItem>
{
    public ClassDescTransitionDrawableItem(ClassDescDrawableMgr classMgr,ClassDescLayerDrawableItem parentClass)
    {
        super(classMgr,"transition:item",parentClass);
    }

    @Override
    public Class<TransitionDrawableItem> getDrawableOrElementDrawableClass()
    {
        return TransitionDrawableItem.class;
    }

    @Override
    public ElementDrawableChild createChildElementDrawable(DOMElement domElement, DOMElement domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        return new TransitionDrawableItem(parentChildDrawable);
    }

    protected void init()
    {
        super.init();

        // addAttrDesc(new AttrDescDrawableReflecMethodDimensionFloatFloor(this,"bottom"));


        //addAttrDesc(new AttrDescDrawableReflecMethodDrawable(this,"drawable"));

        /*
        addAttrDesc(new AttrDescDrawableReflecMethodDimensionIntFloor(this,"bottom"));

        addAttrDesc(new AttrDescDrawableReflecMethodId(this,"id"));
        addAttrDesc(new AttrDescDrawableReflecMethodDimensionIntFloor(this,"left"));
        // android:paddingMode es level 21
        addAttrDesc(new AttrDescDrawableReflecMethodDimensionIntFloor(this,"right"));
        addAttrDesc(new AttrDescDrawableReflecMethodDimensionIntFloor(this,"top"));
        */
    }

}
