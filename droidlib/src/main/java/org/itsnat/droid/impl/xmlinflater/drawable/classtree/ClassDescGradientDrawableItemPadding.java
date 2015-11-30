package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemPadding;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntFloor;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescGradientDrawableItemPadding extends ClassDescElementDrawableChildNormal<GradientDrawableItemPadding>
{
    public ClassDescGradientDrawableItemPadding(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"shape:padding");
    }

    @Override
    public Class<GradientDrawableItemPadding> getDrawableOrElementDrawableClass()
    {
        return GradientDrawableItemPadding.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElement domElement, DOMElement domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        return new GradientDrawableItemPadding(parentChildDrawable);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescReflecMethodDimensionIntFloor(this, "left", 0f));
        addAttrDesc(new AttrDescReflecMethodDimensionIntFloor(this, "top", 0f));
        addAttrDesc(new AttrDescReflecMethodDimensionIntFloor(this, "right", 0f));
        addAttrDesc(new AttrDescReflecMethodDimensionIntFloor(this, "bottom", 0f));
    }

}
