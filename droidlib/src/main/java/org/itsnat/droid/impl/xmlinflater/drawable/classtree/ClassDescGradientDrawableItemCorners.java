package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemCorners;
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
    public ElementDrawableChild createElementDrawableChild(DOMElemDrawable domElement, DOMElemDrawable domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        return new GradientDrawableItemCorners(parentChildDrawable);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "radius", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "topLeftRadius", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "topRightRadius", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "bottomRightRadius", 0f));
        addAttrDescAN(new AttrDescReflecMethodDimensionIntRound(this, "bottomLeftRadius", 0f));
    }

}
