package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemSize;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntRound;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescGradientDrawableItemSize extends ClassDescElementDrawableChildNormal<GradientDrawableItemSize>
{
    public ClassDescGradientDrawableItemSize(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"shape:size");
    }

    @Override
    public Class<GradientDrawableItemSize> getDrawableOrElementDrawableClass()
    {
        return GradientDrawableItemSize.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElemDrawable domElement, DOMElemDrawable domElementParent, ElementDrawable parentChildDrawable, AttrDrawableContext attrCtx)
    {
        return new GradientDrawableItemSize(parentChildDrawable);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "width", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "height", 0f));
    }

}
