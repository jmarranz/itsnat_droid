package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemPadding;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
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
    public ElementDrawableChild createElementDrawableChild(DOMElemDrawable domElement, DOMElemDrawable domElementParent, ElementDrawable parentChildDrawable, AttrDrawableContext attrCtx)
    {
        return new GradientDrawableItemPadding(parentChildDrawable);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodDimensionIntFloor(this, "left", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntFloor(this, "top", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntFloor(this, "right", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntFloor(this, "bottom", 0f));
    }

}
