package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.InsetDrawable;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDesc;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescDrawable_AnimationDrawable_visible extends AttrDesc<ClassDescDrawable,AnimationDrawable,AttrDrawableContext>
{
    public AttrDescDrawable_AnimationDrawable_visible(ClassDescDrawable parent)
    {
        super(parent,"visible");
    }

    @Override
    public void setAttribute(AnimationDrawable draw, DOMAttr attr, AttrDrawableContext attrCtx)
    {
        boolean visible = getBoolean(attr.getValue(), attrCtx.getContext());
        boolean restart = true; // Ni idea
        draw.setVisible(visible, restart);
    }

    @Override
    public void removeAttribute(AnimationDrawable target, AttrDrawableContext attrCtx)
    {
        setToRemoveAttribute(target, "true", attrCtx);
    }
}
