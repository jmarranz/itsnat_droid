package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBase;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableChildSize;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntRound;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescGradientDrawableChildSize extends ClassDescElementDrawableChildNormal<GradientDrawableChildSize>
{
    public ClassDescGradientDrawableChildSize(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"shape:size",null);
    }

    @Override
    public Class<GradientDrawableChildSize> getDrawableOrElementDrawableClass()
    {
        return GradientDrawableChildSize.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElemDrawable domElement, DOMElemDrawable domElementParent, ElementDrawableChildBase parentChildDrawable, AttrDrawableContext attrCtx)
    {
        return new GradientDrawableChildSize(parentChildDrawable);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "width", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "height", 0f));
    }

}
