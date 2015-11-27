package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemGradient;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemSize;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
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
    public ElementDrawableChild createElementDrawableChild(DOMElement domElement, DOMElement domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        return new GradientDrawableItemSize(parentChildDrawable);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescReflecMethodDimensionIntRound(this, "width", 0f));
        addAttrDesc(new AttrDescReflecMethodDimensionIntRound(this, "height", 0f));
    }

}
