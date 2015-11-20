package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.content.Context;

import org.itsnat.droid.impl.dom.DOMElement;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChild;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemGradient;
import org.itsnat.droid.impl.xmlinflated.drawable.GradientDrawableItemSolid;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.XMLInflaterDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodColor;

/**
 * Created by jmarranz on 10/11/14.
 */
public class ClassDescGradientDrawableItemSolid extends ClassDescElementDrawableChildNormal<GradientDrawableItemGradient>
{
    public ClassDescGradientDrawableItemSolid(ClassDescDrawableMgr classMgr)
    {
        super(classMgr,"shape:solid");
    }

    @Override
    public Class<GradientDrawableItemSolid> getDrawableOrElementDrawableClass()
    {
        return GradientDrawableItemSolid.class;
    }

    @Override
    public ElementDrawableChild createElementDrawableChild(DOMElement domElement, DOMElement domElementParent, XMLInflaterDrawable inflaterDrawable, ElementDrawable parentChildDrawable, Context ctx)
    {
        return new GradientDrawableItemSolid(parentChildDrawable);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDesc(new AttrDescReflecMethodColor(this, "color", 0));
    }

}
