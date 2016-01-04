package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemStroke;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodColor;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionFloat;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntRound;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescGradientDrawableItemStroke extends ClassDescElementDrawableChildNormal<GradientDrawableItemStroke>
{
    public ClassDescGradientDrawableItemStroke(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"shape:stroke");
    }

    @Override
    public Class<GradientDrawableItemStroke> getDrawableOrElementDrawableClass()
    {
        return GradientDrawableItemStroke.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElemDrawable domElement, DOMElemDrawable domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        return new GradientDrawableItemStroke(parentChildDrawable);
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
