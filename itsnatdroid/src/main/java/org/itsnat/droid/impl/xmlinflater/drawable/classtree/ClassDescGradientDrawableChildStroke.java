package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildBase;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableChildStroke;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodColor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionFloat;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntRound;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescGradientDrawableChildStroke extends ClassDescElementDrawableChildNormal<GradientDrawableChildStroke>
{
    public ClassDescGradientDrawableChildStroke(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"shape:stroke");
    }

    @Override
    public Class<GradientDrawableChildStroke> getDrawableOrElementDrawableClass()
    {
        return GradientDrawableChildStroke.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElemDrawable domElement, DOMElemDrawable domElementParent, ElementDrawableChildBase parentChildDrawable, AttrDrawableContext attrCtx)
    {
        return new GradientDrawableChildStroke(parentChildDrawable);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "width", 0f));
        addAttrDescAN(new AttrDescReflecMethodColor(this, "color", 0));
        addAttrDescAN(new AttrDescReflecMethodDimensionFloat(this, "dashGap", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionFloat(this, "dashWidth", 0f));
    }

}
