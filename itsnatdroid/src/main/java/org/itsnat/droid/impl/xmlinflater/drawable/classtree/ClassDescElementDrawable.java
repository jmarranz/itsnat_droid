package org.itsnat.droid.impl.xmlinflater.drawable.classtree;

import android.graphics.drawable.Drawable;

import org.itsnat.droid.impl.dom.drawable.DOMElemDrawable;
import org.itsnat.droid.impl.xmlinflated.drawable.ElementDrawableChildRoot;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.ClassDescDrawableMgr;
import org.itsnat.droid.impl.xmlinflater.drawable.attr.AttrDescDrawable_Drawable_visible;

/**

 ClassDescElementDrawable
        ClassDescDrawableContainer  // android.graphics.drawable.DrawableContainer
            ClassDescAnimationDrawable
            ClassDescLevelListDrawable
            ClassDescStateListDrawable
        ClassDescDrawableWrapper // No es level 16
            ClassDescClipDrawable
            ClassDescInsetDrawable
            ClassDescRotateDrawable
            ClassDescScaleDrawable
        ClassDescBitmapDrawable
        ClassDescTransitionDrawable
        ClassDescColorDrawable
        ClassDescNinePatchDrawable
        ClassDescGradientDrawable
        ClassDescLayerDrawable

 * Created by jmarranz on 27/11/14.
 */
public class ClassDescElementDrawable extends ClassDescElementDrawableBased<Drawable>
{
    public ClassDescElementDrawable(ClassDescDrawableMgr classMgr)
    {
        super(classMgr, "NONE",null); // Drawable no tiene clase base
    }

    @Override
    public ElementDrawableChildRoot createElementDrawableChildRoot(DOMElemDrawable rootElem, AttrDrawableContext attrCtx)
    {
        return null; // no hay un <drawable>
    }

    @Override
    public Class<Drawable> getDrawableOrElementDrawableClass()
    {
        return Drawable.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescDrawable_Drawable_visible(this));
    }
}

