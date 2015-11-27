package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemCorners;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemGradient;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDimensionIntRound;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescGradientDrawableItemCorners extends ClassDescElementDrawableChildNormal<GradientDrawableItemCorners>
{
    public ClassDescGradientDrawableItemCorners(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"shape:corners");
    }

    @Override
    public Class<GradientDrawableItemCorners> getDrawableOrElementDrawableClass()
    {
        return GradientDrawableItemCorners.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElement domElement, DOMElement domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        return new GradientDrawableItemCorners(parentChildDrawable);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescReflecMethodDimensionIntRound(this, "radius", 0f));
        addAttrDesc(new AttrDescReflecMethodDimensionIntRound(this, "topLeftRadius", 0f));
        addAttrDesc(new AttrDescReflecMethodDimensionIntRound(this, "topRightRadius", 0f));
        addAttrDesc(new AttrDescReflecMethodDimensionIntRound(this, "bottomRightRadius", 0f));
        addAttrDesc(new AttrDescReflecMethodDimensionIntRound(this, "bottomLeftRadius", 0f));
    }

}
