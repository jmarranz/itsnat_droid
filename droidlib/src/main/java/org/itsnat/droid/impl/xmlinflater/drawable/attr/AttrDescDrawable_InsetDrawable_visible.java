package org.itsnat.droid.impl.xmlinflater.drawable.attr;

import android.graphics.drawable.InsetDrawable;

import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.xmlinflater.drawable.AttrDrawableContext;
import org.itsnat.droid.impl.xmlinflater.drawable.classtree.ClassDescDrawable;

/**
 * Created by jmarranz on 30/04/14.
 */
public class AttrDescDrawable_InsetDrawable_visible extends AttrDescDrawable<InsetDrawable>
{
    public AttrDescDrawable_InsetDrawable_visible(ClassDescDrawable parent)
    {
        super(parent,"visible");
    }

    @Override
    public void setAttribute(InsetDrawable draw, DOMAttr attr, AttrDrawableContext attrCtx)
    {
        boolean restart = true; // Ni idea
        draw.setVisible(getBoolean(attr.getValue(),attrCtx.getContext()),restart);
    }
}
